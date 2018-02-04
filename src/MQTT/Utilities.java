package MQTT;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Created by Kalaman on 24.01.18.
 */
public class Utilities {

    public static String getMACAdress () {
        NetworkInterface network = null;
        try {
            network = NetworkInterface.getByInetAddress(getIPAdress());
            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static InetAddress getIPAdress () {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        }catch (Exception e){e.printStackTrace();}

        return ip;
    }
}
