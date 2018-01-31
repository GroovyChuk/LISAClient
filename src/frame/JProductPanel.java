package frame; /**
 * Created by alasdair on 28.01.18.
 */

import HTTPRest.Request;
import main.JConstants;
import main.RFIDReaderThread;
import org.json.simple.JSONObject;
import product.NutritionFact;
import product.Product;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JProductPanel extends JPanel implements ListSelectionListener, RFIDReaderThread.RFIDReader{

    private JList list;
    private DefaultListModel listModel;
    private Thread readerThread;
    private ArrayList<Product> cartItems = new ArrayList<Product>();


    private JButton removeButton;

    public JProductPanel() {
        setPreferredSize(new Dimension(JConstants.WINDOW_SIZE_X/2, JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<JMainFrame>();



        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);


        removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveListener());


        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(Box.createHorizontalStrut(5));

        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        readerThread = new RFIDReaderThread(this);
        readerThread.start();
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    public void addProduct (Product product) {
        listModel.insertElementAt(product.getName() + "   " + product.getPrice() + " €",0);
        cartItems.add(product);
        updateUI();
    }

    public void removeProduct (Product product) {
        for (int i = 0; i < listModel.size(); i++) {
            if (listModel.get(i).toString().contains(product.getName()))
                listModel.remove(i);
            if (cartItems.get(i).getsGTIN() == product.getsGTIN())
                cartItems.remove(i);
        }

        updateUI();
    }

    public void onRFIDScanned(String rfidCode) {
        System.out.println(rfidCode);
        Request request = new Request();

        try {
            JSONObject jsonObject = request.getURL(new URL("http://localhost:5000/products/"+rfidCode));
            Product product = new Product(jsonObject.get("Name").toString(), "Rewe",
                    Float.valueOf(jsonObject.get("Preis").toString()),"Getränk" ,
                    new NutritionFact(0,0.0f,0.0f));

            Thread add = new Thread() {
                @Override
                public void run() {
                    try {
                        boolean added = false;
                        for (Product cartItem : cartItems) {
                            if (cartItem.getsGTIN() == product.getsGTIN())
                                added = true;
                        }
                        if (!added) { // insert check for serial number here
                            addProduct(product);
                        } else {
                            removeProduct(product);
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

}
