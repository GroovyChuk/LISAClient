package main;

import MQTT.MQTTClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static MQTTClient mqttClient;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../files/Initial_Screen.fxml"));
        primaryStage.setTitle("LISA Client v0.01");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        mqttClient = new MQTTClient();
        mqttClient.startSessionThread();
        launch(args);
    }
}
