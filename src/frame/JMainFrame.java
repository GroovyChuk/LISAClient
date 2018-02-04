package frame;

import main.JConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kalaman on 12.01.18.
 */
public class JMainFrame extends JFrame {

    JProductPanel jProductPanel;
    JProductDetailsPanel jProductDetailsPanel;


    public JMainFrame () {
        super("LISAClient");

        jProductDetailsPanel = new JProductDetailsPanel();
        jProductPanel = new JProductPanel(jProductDetailsPanel);


        this.setSize(new Dimension(JConstants.WINDOW_SIZE_X, JConstants.WINDOW_SIZE_Y));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.add(jProductPanel,BorderLayout.WEST);
        this.add(jProductDetailsPanel, BorderLayout.EAST);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }


}
