package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;


/**
 * Created by alasdair on 09.01.18.
 */
public class MainScreen{

    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView imageUser;
    @FXML
    ListView<Product> listviewCart;
    @FXML
    Label labelProductSum;
    @FXML
    Pane paneEmptyShoppingCart;

    private ObservableList<Product> cartList = FXCollections.observableArrayList();


    @FXML
    private void initialize() throws IOException {

        addProduct(new Product("Snickers",1.5f));
        addProduct(new Product("Mars",1f));
        addProduct(new Product("Macadamia",2.3f));
        addProduct(new Product("Peanuts",0.5f));

        listviewCart.setCellFactory(new Callback<ListView<Product>, ListCell<Product>>() {
            @Override
            public ListCell<Product> call(ListView<Product> param) {
                return new ProductListCell();
            }
        });

        listviewCart.setItems(cartList);
        updateUI();
    }

    public void addProduct (Product product) {
        cartList.add(product);
        updateUI();
    }

    public void removeProduct (Product product) {
        cartList.remove(product);
        updateUI();
    }

    public void updateUI() {
        float productSum= 0;

        for (Product product : cartList)
            productSum += product.getPrice();

        labelProductSum.setText("Summe: " + productSum + " €");

        if(cartList.size() > 0 )
            paneEmptyShoppingCart.setVisible(false);
        else
            paneEmptyShoppingCart.setVisible(true);
    }

}
