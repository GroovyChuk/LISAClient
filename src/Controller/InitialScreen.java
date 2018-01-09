package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import qrcode.QrCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InitialScreen {

    @FXML
    ImageView barcode;

    @FXML
    GridPane gridPane;

    @FXML
    javafx.scene.control.Button weiter;

    public InitialScreen() {

    }

    @FXML
    private void proceedButton(javafx.event.ActionEvent event) throws IOException{
        gridPane.getChildren().clear();
        gridPane.getChildren().add(FXMLLoader.load(getClass().getResource("../files/UI.fxml")));
    }

    @FXML
    private void initialize() throws IOException {
        // Simple operation

        QrCode qr0 = QrCode.encodeText("UID", QrCode.Ecc.MEDIUM);
        BufferedImage img = qr0.toImage(10, 10);
        File file = new File("qr-code.png");
        ImageIO.write(img, "png", file);
        barcode.setImage(new Image(new FileInputStream(file)));
    }

}
