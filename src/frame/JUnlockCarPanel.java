package frame;

import HTTPRest.Request;
import MQTT.MQTTClient;
import main.App;
import qrcode.QrCode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static frame.JMainFrame.jProductDetailsPanel;
import static frame.JMainFrame.jProductPanel;

/**
 * Created by Kalaman on 02.02.18.
 */
public class JUnlockCarPanel extends JPanel implements MQTTClient.MQTTListener{

    QrCode qrCode;
    BufferedImage qrImageView;
    JFrame jMainFrame;

    public JUnlockCarPanel (JFrame jMainFrame) {
        this.setLayout(new BorderLayout());
        this.jMainFrame = jMainFrame;

        App.mqttClient.addMQTTListener(this);

        try {
            String session = Request.getNewSessionKey();
            QrCode qr0 = QrCode.encodeText(session, QrCode.Ecc.MEDIUM);
            BufferedImage img = qr0.toImage(10, 10);
            File file = new File(session + ".png");
            ImageIO.write(img, "png", file);

            qrImageView = ImageIO.read(new File(session + ".png"));
            add(new JLabel(new ImageIcon(qrImageView)));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSessionStarted() {
        jMainFrame.remove(this);
        jMainFrame.add(jProductPanel,BorderLayout.WEST);
        jMainFrame.add(jProductDetailsPanel, BorderLayout.EAST);
    }

}
