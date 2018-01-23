package main;

import MQTT.MQTTClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

    public static MQTTClient mqttClient;
    public static Stage window;
    public static Scene loginScene;
    public static Scene mainScene;
    public static String PYTHON_SCRIPT_PATH = "";

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        loginScene = new Scene(FXMLLoader.load(getClass().getResource("/layouts/login_screen.fxml")), 800, 400);
        mainScene = new Scene(FXMLLoader.load(getClass().getResource("/layouts/main_screen.fxml")), 800, 400);

        window.setTitle("LISA Client v0.01");
        window.setScene(mainScene);
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
                public void handle(WindowEvent event) {
                try {
                    mqttClient.stopSessionThread();
                    stop();
                } catch (Exception e) {
                     e.printStackTrace();
                }
            }
        });
        window.show();
    }

    public static void main(String[] args) {
        PYTHON_SCRIPT_PATH = args[0];

        System.out.println("Python-Script-Path: " + PYTHON_SCRIPT_PATH);

        mqttClient = new MQTTClient();
        mqttClient.startSessionThread();
        launch(args);
    }
}
