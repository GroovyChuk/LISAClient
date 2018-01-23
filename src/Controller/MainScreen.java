package Controller;

import HTTPRest.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import main.RFIDReaderThread;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alasdair on 09.01.18.
 */
public class MainScreen implements RFIDReaderThread.RFIDReader{

    @FXML
    AnchorPane anchorPane;
    @FXML
    AnchorPane anchorPaneCurrentProduct;
    @FXML
    AnchorPane anchorPaneCurrentProductNutInfo;
    @FXML
    ImageView imageUser;
    @FXML
    ListView<Product> listviewCart;
    @FXML
    Label labelProductSum;
    @FXML
    Pane paneEmptyShoppingCart;

    @FXML
    Label labelProductName;
    @FXML
    Label labelProductPrice;
    @FXML
    Label labelProductProducer;
    @FXML
    Label labelProductType;

    @FXML
    Label labelProductCalories;
    @FXML
    Label labelProductCarbohydrate;
    @FXML
    Label labelProductFat;

    @FXML
    ImageView imageViewProductType;

    private ObservableList<Product> cartList = FXCollections.observableArrayList();
    private Thread readerThread;

    @FXML
    private void initialize() throws IOException {


        listviewCart.setCellFactory(new Callback<ListView<Product>, ListCell<Product>>() {
            @Override
            public ListCell<Product> call(ListView<Product> param) {
                return new ProductListCell(MainScreen.this);
            }
        });

        listviewCart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                updateUI();
            }
        });

        listviewCart.setItems(cartList);
        updateUI();

        readerThread = new RFIDReaderThread(this);
        readerThread.start();
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
        int selectedIndex = listviewCart.getSelectionModel().getSelectedIndices().get(0);
        Product selectedProduct = null;

        if (selectedIndex >= 0) {
            selectedProduct = cartList.get(selectedIndex);

            if (selectedProduct.getProductType() == ProductTypes.OBJECT)
                anchorPaneCurrentProductNutInfo.setVisible(false);
            else {
                anchorPaneCurrentProductNutInfo.setVisible(true);
                labelProductCarbohydrate.setText(selectedProduct.getNutritionFact().getFormatedCarbohydrates());
                labelProductCalories.setText(selectedProduct.getNutritionFact().getFormatedCalories());
                labelProductFat.setText(selectedProduct.getNutritionFact().getFormatedTotalFat());
            }

            labelProductName.setText(selectedProduct.getName());
            labelProductPrice.setText(selectedProduct.getFormatedPrice());
            labelProductProducer.setText(selectedProduct.getProducer());
            labelProductType.setText(selectedProduct.getProductType());

            float productSum = 0;

            for (Product product : cartList)
                productSum += product.getPrice();

            labelProductSum.setText("Summe: " + String.format("%.2f",productSum) + " €");
            paneEmptyShoppingCart.setVisible(false);
            anchorPaneCurrentProduct.setVisible(true);

            Image productTypeImage = null;

            switch (selectedProduct.getProductType()) {
                case ProductTypes.VEGAN:
                    productTypeImage = new Image("/img/vegan.png");
                    break;
                case ProductTypes.VEGETARIAN:
                    productTypeImage = new Image("/img/vegetarian.png");
                    break;
                case ProductTypes.MEAT:
                    productTypeImage = new Image("/img/meat.png");
                    break;
                case ProductTypes.OBJECT:
                    productTypeImage = new Image("/img/product.png");
                    break;
            }

            imageViewProductType.setImage(productTypeImage);
        }
        else
        {
            float productSum = 0;

            for (Product product : cartList)
                productSum += product.getPrice();

            labelProductSum.setText("Summe: " + String.format("%.2f",productSum) + " €");

            paneEmptyShoppingCart.setVisible(true);
            anchorPaneCurrentProduct.setVisible(false);
            anchorPaneCurrentProductNutInfo.setVisible(false);

        }
    }


    @Override
    public void onRFIDScanned(String rfidCode) {
        System.out.println(rfidCode);
        Request request = new Request();

        try {
            JSONObject jsonObject = request.getURL(new URL("http://localhost:5000/products/"+rfidCode));
            Product product = new Product(jsonObject.get("Name").toString(), "Rewe",
                    Float.valueOf(jsonObject.get("Preis").toString()),"Getränk" ,
                    new NutritionFact(0,0.0f,0.0f));
            addProduct(product);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
