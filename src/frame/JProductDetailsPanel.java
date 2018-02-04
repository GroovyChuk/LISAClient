package frame;

import main.JConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alasdair on 31.01.18.
 */
public class JProductDetailsPanel extends JPanel{

    private JLabel jProductNameF,jProducerF,jProductTypeF,jProductPriceF;

    public JProductDetailsPanel() {

        setPreferredSize(new Dimension(JConstants.WINDOW_SIZE_X/2, JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());

        JPanel jGeneralPanel = new JPanel();
        jGeneralPanel.setLayout(null);

        JLabel jProducer = new JLabel("Hersteller");
        JLabel jProductType = new JLabel("Produkttyp");
        JLabel jProductPrice = new JLabel("Preis");

        Font font = jProducer.getFont();

        jProducer.setBounds(10,50, 100,20);
        jProductType.setBounds(200,50, 100,20);
        jProductPrice.setBounds(10,100, 100,20);


        jProductNameF = new JLabel("Produktdetails");
        jProducerF = new JLabel("");
        jProductTypeF = new JLabel("");
        jProductPriceF = new JLabel("");
        jProductNameF.setHorizontalAlignment(SwingConstants.CENTER);

        jProductNameF.setFont(font.deriveFont(font.getStyle() & Font.BOLD));
        jProducerF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
        jProductTypeF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
        jProductPriceF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));

        jProducerF.setBounds(110,50, 90,20);
        jProductTypeF.setBounds(300,50, 100,20);
        jProductPriceF.setBounds(110,100, 100,20);


        jGeneralPanel.add(jProducer);
        jGeneralPanel.add(jProductType);
        jGeneralPanel.add(jProductPrice);
        jGeneralPanel.add(jProducerF);
        jGeneralPanel.add(jProductTypeF);
        jGeneralPanel.add(jProductPriceF);

        JLabel jNutrition = new JLabel("Nährwerte");
        jNutrition.setFont(font.deriveFont(font.getStyle() & Font.BOLD));
        jNutrition.setBounds((JConstants.WINDOW_SIZE_X/2-100)/2, JConstants.WINDOW_SIZE_Y/2-30, 100,20);

        jGeneralPanel.add(jNutrition);
        add(jGeneralPanel);
        add(jProductNameF, BorderLayout.PAGE_START);
    }

    public void setLabelSize(JLabel jLabel, Dimension d){
        jLabel.setMinimumSize(d);
        jLabel.setPreferredSize(d);
        jLabel.setMaximumSize(d);
    }

    public void setProductDetails(String name, String producer, String type, String price){
        jProductNameF.setText(name);
        jProducerF.setText(producer);
        jProductTypeF.setText(type);
        jProductPriceF.setText(price);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,JConstants.WINDOW_SIZE_Y/2-20,JConstants.WINDOW_SIZE_X,JConstants.WINDOW_SIZE_Y/2-20);
    }
}
