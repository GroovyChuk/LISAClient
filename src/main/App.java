package main;

import MQTT.Constants;
import MQTT.MQTTClient;
import MQTT.Utilities;
import frame.JMainFrame;
import javax.swing.*;

/**
 * Created by alasdair on 28.01.18.
 */
public class App {

    public static MQTTClient mqttClient;

    public static String PYTHON_SCRIPT_PATH = "";

    public static void main (String[] args) {

        if (args.length == 0) {
            System.out.println("Don't forget the python-script-path idiot !");
            return;
        }
        else{
            PYTHON_SCRIPT_PATH = args[0];
            System.out.println("Python-Script-Path: " + PYTHON_SCRIPT_PATH);
        }
        Constants.MAC_ADRESSE = Utilities.getMACAdress();

        mqttClient = new MQTTClient();
        mqttClient.startSessionThread();

        JMainFrame frame = new JMainFrame();

    }
}