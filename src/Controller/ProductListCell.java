package Controller;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

public class ProductListCell extends ListCell<Product>{

    GridPane gridPane;
    Label labelName;
    Label labelPrice;
    ImageView imageViewDelete;

    public ProductListCell() {
        gridPane = new GridPane();
        gridPane.setHgap(3);
        gridPane.setVgap(3);
        //gridPane.setPadding(new Insets(0, 5, 0, 10));

        labelName = new Label("");
        labelPrice = new Label("");

        gridPane.add(labelName, 1, 0);
        gridPane.add(labelPrice, 1, 1);

        try {
            Image img = new Image("file:delete_30px.png");
            imageViewDelete = new ImageView(img);
            gridPane.add(imageViewDelete, 2, 1);
        }catch (Exception e){
            e.printStackTrace();
        }
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
