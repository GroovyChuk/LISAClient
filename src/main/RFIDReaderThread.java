package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                ProcessBuilder pb = new ProcessBuilder("python", App.RFID_SCRIPT_PATH);

                Process p = pb.start();

                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                while ((line = in.readLine()) != null) {
                    if (line.length() == 16)
                        listener.onRFIDScanned(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
