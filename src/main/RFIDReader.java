package main;


import java.io.*;


/**
 * Created by alasdair on 22.01.18.
 */
public class RFIDReader {

    private Thread readerThread;

    public RFIDReader(){


        readerThread = new Thread() {
            @Override
            public void run() {
            try {

                BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
                out.close();

                ProcessBuilder pb = new ProcessBuilder("python","/home/alasdair/IdeaProjects/LISAClient/src/scripts/test.py");
                Process p = pb.start();
                String line;
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                    e.printStackTrace();
            }
        };
    };
    }

    public void startReaderThread () {
        readerThread.start();
    }
    public void stopReaderThread () {
        readerThread.stop();
    }

}
