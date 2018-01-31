package frame;

import main.JConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alasdair on 31.01.18.
 */
public class JProductDetailsPanel extends JPanel{

    public JProductDetailsPanel() {

        setPreferredSize(new Dimension(JConstants.WINDOW_SIZE_X/2, JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());
    }
}
