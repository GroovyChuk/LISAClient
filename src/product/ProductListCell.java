package product;

import frame.JMainFrame;
import frame.JProductPanel;
import main.JConstants;

import javax.swing.*;
import java.awt.*;

public class ProductListCell extends JPanel{

    private JLabel labelName;
    private JLabel labelPrice;
//    ImageView imageViewDelete;
    JMainFrame mainFrame;

    public ProductListCell(){
        setPreferredSize(new Dimension(JConstants.WINDOW_SIZE_X/2, 20));
        setLayout(new BorderLayout());

        this.mainFrame = mainFrame;


        labelName = new JLabel("Ja");
        labelPrice = new JLabel("");

        this.add(labelName);
//        jPanel.add(labelPrice,1);


        try {
//            Image img = new Image("/img/delete_25px.png");
//            imageViewDelete = new ImageView(img);
//            gridPane.add(imageViewDelete, 1, 0);
//            gridPane.setHalignment(imageViewDelete, HPos.RIGHT);
        }catch (Exception e){
            e.printStackTrace();
        }

//        imageViewDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                mainScreen.removeProduct(getItem());
//            }
//        });
    }


    protected void updateItem(Product item, boolean empty) {
//        super.updateItem(item, empty);

        if (empty) {
//            setGraphic(null);
        } else {
            labelName.setText(item.getName());
            labelPrice.setText(item.getFormatedPrice());

//            setGraphic(gridPane);
        }
    }

}
