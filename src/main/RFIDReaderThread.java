package main;


import Controller.MainScreen;
import Controller.NutritionFact;
import Controller.Product;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alasdair on 22.01.18.
 */
public class RFIDReaderThread extends Thread{

    RFIDReader listener;

    public RFIDReaderThread(RFIDReader listener){
        this.listener = listener;
    }

    public interface RFIDReader {
        public void onRFIDScanned(String rfidCode);
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                ProcessBuilder pb = new ProcessBuilder("python", App.PYTHON_SCRIPT_PATH);
                Process p = pb.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                while ((line = in.readLine()) != null) {
                    listener.onRFIDScanned(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    /home/alasdair/IdeaProjects/LISAClient/src/scripts/Read.py


}
