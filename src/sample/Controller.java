package sample;

import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import qrcode.QrCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Controller {

    @FXML
    ImageView image;

    public Controller () {

    }

    @FXML
    private void initialize() throws IOException {
        // Simple operation

        QrCode qr0 = QrCode.encodeText("Buy TRON!", QrCode.Ecc.MEDIUM);
        BufferedImage img = qr0.toImage(10, 10);
        File file = new File("qr-code.png");
        ImageIO.write(img, "png", file);
        image.setImage(new Image(new FileInputStream(file)));
    }

}
