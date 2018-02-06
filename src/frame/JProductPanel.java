package frame; /**
 * Created by alasdair on 28.01.18.
 */

import HTTPRest.Request;
import main.App;
import main.JConstants;
import main.RFIDReaderThread;
import main.ScalesReaderThread;
import org.json.simple.JSONObject;
import product.NutritionFact;
import product.Product;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static frame.JMainFrame.jProductDetailsPanel;
import static frame.JMainFrame.jProductPanel;
import static frame.JMainFrame.jUnlockCarPanel;

public class JProductPanel extends JPanel implements ListSelectionListener, ScalesReaderThread.ScalesReader, RFIDReaderThread.RFIDReader{

    private JList list;
    private DefaultListModel listModel;
    private Thread readerThread , scalesThread;
    private ArrayList<Product> cartItems;
    private float cartSum = 0.00f;
    private JLabel sumLabel , weightLabel, scaleLabel;
    private JProductDetailsPanel jProductDetailsPanel;
    private int weight = 0;
    private JButton purchaseButton;
    private JMainFrame jMainFrame;
    private JUnlockCarPanel jUnlockCarPanel;

    public JProductPanel(JProductDetailsPanel jProductDetails, JUnlockCarPanel jUnlockCarPanel, JMainFrame jMainFrame) {
        this.jProductDetailsPanel = jProductDetails;
        this.jMainFrame = jMainFrame;
        this.jUnlockCarPanel = jUnlockCarPanel;
        init();
    }




    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                jProductDetailsPanel.setProductDetails("Produktdetails","","","", "");
                jProductDetailsPanel.enableNutritionalValues(false);

            } else if (list.getSelectedIndex() != -1){
                int index = 0, productIndex = 0;
                Product p;
                for (int i = 0; i < listModel.size(); i++) {
                    for (int j = 0; j < cartItems.size(); j++) {
                        if (listModel.get(i).toString().contains(cartItems.get(j).getName())){
                            index = i;
                            productIndex = j;
                        }
                    }

                }
                p = cartItems.get(index);
                int occurrence = 0;
                //find occurrence of same Products
                for (int i = 0; i < cartItems.size(); i++) {
                    if (cartItems.get(i).getsGTIN().substring(0,13).equals(cartItems.get(productIndex).getsGTIN().substring(0,13)))
                        occurrence++;
                }
                jProductDetailsPanel.setProductDetails(p.getName(),p.getProducer(),p.getProductType(),p.getFormatedPrice(),"" + occurrence);
                if (p.getProductType().equalsIgnoreCase("Lebensmittel")){
                    jProductDetailsPanel.enableNutritionalValues(true);
                    jProductDetailsPanel.setNutritionalValues(p.getNutritionFact().getCalories() + " kcal",p.getNutritionFact().getTotalFat() + " g",p.getNutritionFact().getCarbohydrates() + " g");
                }
            }
        }
    }


    public void addProduct (Product product) {
        int occurrence = 1;
        //find occurrence of same Products
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getsGTIN().substring(0,13).equals(product.getsGTIN().substring(0,13)))
                occurrence++;
        }
        if (occurrence == 1)
            listModel.insertElementAt(product.getName() + "   " + product.getPrice() + " €",0);
        else {
            for (int i = 0; i < listModel.size(); i++) {
                if (listModel.get(i).toString().contains(product.getName()))
                    listModel.removeElementAt(i);
            }
            listModel.insertElementAt(occurrence + "x " + product.getName() + "   " + (product.getPrice() * occurrence) + " €", 0);
        }
        cartItems.add(product);
        cartSum += product.getPrice();
        updateCartSum();
        updateUI();
    }

    public void removeProduct (Product product) {
        int occurrence = 0, removeIndex = -1;

        //find occurrence of same Products
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getsGTIN().substring(0,13).equals(product.getsGTIN().substring(0,13))) {
                occurrence++;
                if (cartItems.get(i).getsGTIN().equals(product.getsGTIN()))
                    removeIndex = i;
            }
                       }
        if (removeIndex != -1){
            cartItems.remove(removeIndex);
            occurrence--;
        }

        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.get(i).toString().contains(product.getName()))
                listModel.remove(i);
                if (occurrence == 0) {
                    break;
                } else if (occurrence == 1) {
                    listModel.insertElementAt(product.getName() + "   " + product.getPrice() + " €",i);

                } else {
                    listModel.insertElementAt(occurrence + "x " + product.getName() + "   " + (product.getPrice() * occurrence) + " €", i);
                }

        }

        cartSum -= product.getPrice();
        updateCartSum();
        updateUI();
    }

    public void onRFIDScanned(String rfidCode) {
        System.out.println(rfidCode);
        Request request = new Request();

        try {
            JSONObject jsonObject = request.getURL(new URL("http://" + App.IP + ":5000/products/" + rfidCode.substring(0,13)));
            Product product = new Product(jsonObject.get("Name").toString(), jsonObject.get("Hersteller").toString(),
                    Float.valueOf(jsonObject.get("Preis").toString()),jsonObject.get("Typ").toString() ,
                    new NutritionFact(Integer.parseInt(jsonObject.get("Kalorien").toString()),Integer.parseInt(jsonObject.get("Kohlenhydrate").toString()),Integer.parseInt(jsonObject.get("Fett").toString())),rfidCode, Integer.parseInt(jsonObject.get("Gewicht").toString()));
            Thread add = new Thread() {
                @Override
                public void run() {
                    try {
                        boolean added = false;
                        for (Product cartItem : cartItems) {
                            if (cartItem.getsGTIN().equals(product.getsGTIN()))
                                added = true;
                        }
                        if (!added) {
//                            while ((product.getWeight() * 0.7 > weight ) || (product.getWeight() * 1.3 < weight)){
//                                scaleLabel.setVisible(true);
//                            }
                            ProcessBuilder pb = new ProcessBuilder("python", "/home/pi/Documents/greenled.py");
                            scaleLabel.setVisible(false);
                            addProduct(product);
                            Process p = pb.start();

                        } else {
                            removeProduct(product);
                            ProcessBuilder pb = new ProcessBuilder("python", "/home/pi/Documents/redled.py");
                            Process p = pb.start();
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            };
            add.run();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public void onScalesScanned(String weight) {
        System.out.println(weight);
        this.weight = Integer.parseInt(weight);
        updateScales(this.weight);
    }


    public void startCartThreads() {

//        ProcessBuilder tare = new ProcessBuilder("python", App.SCALES_TARE_SCRIPT_PATH);
//        try {
//            tare.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        scalesThread.start();
        readerThread.start();
    }

    public void updateCartSum(){
        sumLabel.setText("Summe: " + cartSum + " €");
    }

    public void updateScales(int weight){
        weightLabel.setText("Gewicht: " + weight + " g");
    }

    class PurchaseListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JSONObject jsonPurchase = new JSONObject();
            try {
                URL purchase = new URL("http://" + App.IP + ":5000/session/purchase/" + App.sessionKey + "/" + cartSum);
                jsonPurchase = Request.getURL(purchase);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }

            for (Product cartItem : cartItems) {
                try {
                    URL item = new URL("http://" + App.IP + ":5000/session/productpurchase/" + cartItem.getsGTIN().substring(0,13) + "/" + jsonPurchase.get("EID").toString());
                    Request.getURL(item);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                }
            }
            stopSession();
        }
    }

    public void stopSession(){
        jMainFrame.remove(this);
        jMainFrame.remove(jProductDetailsPanel);
        scalesThread.stop();
        readerThread.stop();
        init();
        App.restartMQTT();
        jUnlockCarPanel = new JUnlockCarPanel(jMainFrame);
        jMainFrame.setContentPane(jUnlockCarPanel);
        jMainFrame.invalidate();
        jMainFrame.validate();



    }

    private void init(){
        setPreferredSize(new Dimension(JConstants.WINDOW_SIZE_X/2, JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<JMainFrame>();
        cartItems = new ArrayList<Product>();

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);



        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        sumLabel = new JLabel();
        weightLabel = new JLabel();
        scaleLabel = new JLabel();
        scaleLabel.setText("Bitte stellen Sie den gescannten Artikel auf die Waage.");
        scaleLabel.setVisible(false);
        updateCartSum();
        updateScales(0);
        purchaseButton = new JButton("Kaufen");
        purchaseButton.addActionListener(new PurchaseListener());
        buttonPane.setLayout(new BorderLayout());
        buttonPane.add(sumLabel, BorderLayout.LINE_END);
        buttonPane.add(purchaseButton,BorderLayout.NORTH);
        buttonPane.add(weightLabel, BorderLayout.LINE_START);


        buttonPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        add(scaleLabel, BorderLayout.PAGE_START);
        readerThread = new RFIDReaderThread(this);
        scalesThread = new ScalesReaderThread(this);
    }

}
