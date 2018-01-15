package Controller;

import MQTT.MQTTClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import main.App;
import qrcode.QrCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static main.App.mqttClient;

public class LoginScreen {

    @FXML
    ImageView barcode;

    @FXML
    GridPane gridPane;

    @FXML
    private void initialize() throws IOException {
        mqttClient.addMQTTListener(new MQTTClient.MQTTListener() {
            @Override
            public void onSessionCodeScanned(String sessionID) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        App.window.setScene(App.mainScene);
                    }
                });
            }
        });

        QrCode qr0 = QrCode.encodeText("UID", QrCode.Ecc.MEDIUM);
        BufferedImage img = qr0.toImage(10, 10);
        File file = new File("qr-code.png");
        ImageIO.write(img, "png", file);
        barcode.setImage(new Image(new FileInputStream(file)));
    }

}
