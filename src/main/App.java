package main;

import MQTT.MQTTClient;
import frame.JMainFrame;

import javax.swing.*;

/**
 * Created by alasdair on 28.01.18.
 */
public class App {

    public static MQTTClient mqttClient;

    public static String PYTHON_SCRIPT_PATH = "";

    public static void main (String[] args) {


//        if (args.length == 0)
//        {
//            System.out.println("Don't forget the python-script-path idiot !");
//            return;
//        }
//        else{
//            PYTHON_SCRIPT_PATH = args[0];
//            System.out.println("Python-Script-Path: " + PYTHON_SCRIPT_PATH);
//        }

//        mqttClient = new MQTTClient();
//        mqttClient.startSessionThread();

        JMainFrame frame = new JMainFrame();

    }
}
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.*;
//import javax.swing.*;
//import java.util.*;
//import java.awt.*;
//
//public class App
//{
//    public static void main(String args[]) {
//        JFrame frame = new JFrame("JList ImageIcon Demonstration");
//
//        DefaultListModel dlm = new DefaultListModel();
//        JButton button;
//
//        dlm.addElement(new ListEntry("Audio", new ImageIcon(App.class.getResource("/img/delete_25px.png"))));
//
//
//        JList list = new JList(dlm);
//        list.setCellRenderer(new ListEntryCellRenderer());
//
//        frame.setSize(new Dimension(JConstants.WINDOW_SIZE_X, JConstants.WINDOW_SIZE_Y));
//        frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(list));
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//
//
//    }
//
//}
//
//class ListEntry
//{
//    private String value;
//    private ImageIcon icon;
//
//    public ListEntry(String value, ImageIcon icon) {
//        this.value = value;
//        this.icon = icon;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public ImageIcon getIcon() {
//        return icon;
//    }
//
//    public String toString() {
//        return value;
//    }
//
//}
//
//class ListEntryCellRenderer
//        extends JLabel implements ListCellRenderer
//{
//    private JLabel label;
//
//    public Component getListCellRendererComponent(JList list, Object value,
//                                                  int index, boolean isSelected,
//                                                  boolean cellHasFocus) {
//        ListEntry entry = (ListEntry) value;
//
//        setText(value.toString());
//        setIcon(entry.getIcon());
//
//        if (isSelected) {
//            setBackground(list.getSelectionBackground());
//            setForeground(list.getSelectionForeground());
//        }
//        else {
//            setBackground(list.getBackground());
//            setForeground(list.getForeground());
//        }
//
//        setEnabled(list.isEnabled());
//        setFont(list.getFont());
//        setOpaque(true);
//
//        return this;
//    }
//}