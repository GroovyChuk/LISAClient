package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by alasdair on 22.01.18.
 */
public class ScalesReaderThread extends Thread{

    ScalesReader listener;

    public ScalesReaderThread(ScalesReader listener){ this.listener = listener; }

    public interface ScalesReader {
        public void onScalesScanned(String weight);
    }

    @Override
    public void run() {

        super.run();
        try {
            while (true) {
                ProcessBuilder pb = new ProcessBuilder("python", App.SCALES_SCRIPT_PATH);

                Process p = pb.start();

                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                while ((line = in.readLine()) != null) {
                    if (line.length() != 16)
                        listener.onScalesScanned(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
