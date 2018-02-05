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

    public static String RFID_SCRIPT_PATH = "";
    public static String SCALES_SCRIPT_PATH = "";
    public static String SCALES_TARE_SCRIPT_PATH = "";
    public static String IP = "";
    public static String sessionKey = "";
    public static JMainFrame frame;

    public static void main (String[] args) {


        if (args.length != 4)
        {
            System.out.println("Don't forget the python-script-path idiot !");
            return;
        }
        else{
            RFID_SCRIPT_PATH = args[0];
            SCALES_SCRIPT_PATH = args[1];
            SCALES_TARE_SCRIPT_PATH = args[2];
            IP = args[3];
            System.out.println("Python-Script-Path: " + RFID_SCRIPT_PATH);
            System.out.println("Scales-Tare-Script-Path: " + SCALES_TARE_SCRIPT_PATH);
            System.out.println("Scales-Script-Path: " + SCALES_SCRIPT_PATH);
            System.out.println("REST-Server IP: " + IP);
            System.out.println("------------------------------------------------------------------");
        }
        Constants.MAC_ADRESSE = Utilities.getMACAdress();

        mqttClient = new MQTTClient();
        mqttClient.startSessionThread();

        frame = new JMainFrame();
    }
}