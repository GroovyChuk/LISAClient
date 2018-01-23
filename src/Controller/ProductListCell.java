package Controller;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.File;

public class ProductListCell extends ListCell<Product>{

    GridPane gridPane;
    Label labelName;
    Label labelPrice;
    ImageView imageViewDelete;
    MainScreen mainScreen;

    public ProductListCell(MainScreen mainScr) {
        gridPane = new GridPane();
        gridPane.setHgap(3);
        gridPane.setVgap(3);
        //gridPane.setPadding(new Insets(0, 5, 0, 10));

        mainScreen = mainScr;

        labelName = new Label("");
        labelPrice = new Label("");

        gridPane.add(labelName, 0, 0);
        gridPane.add(labelPrice, 0, 1);

        try {
            Image img = new Image("/img/delete_25px.png");
            imageViewDelete = new ImageView(img);
            gridPane.add(imageViewDelete, 1, 0);
            gridPane.setHalignment(imageViewDelete, HPos.RIGHT);
        }catch (Exception e){
            e.printStackTrace();
        }

        imageViewDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainScreen.removeProduct(getItem());
            }
        });
    }

    @Override
    protected void updateItem(Product item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            labelName.setText(item.getName());
            labelPrice.setText(item.getFormatedPrice());

            setGraphic(gridPane);
        }
    }

}
