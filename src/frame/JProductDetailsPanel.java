package frame;

import main.JConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alasdair on 31.01.18.
 */
public class JProductDetailsPanel extends JPanel{

    private JLabel jProductNameF,jProducerF,jProductTypeF,jProductPriceF,jProductAmountF;
    private JLabel jCaloriesF, jCarbosF, jFatF, jCalories, jCarbos, jFat, jNutrition;



    public JProductDetailsPanel() {

        setPreferredSize(new Dimension(JConstants.WINDOW_SIZE_X/2, JConstants.WINDOW_SIZE_Y));
        setLayout(new BorderLayout());

        JPanel jGeneralPanel = new JPanel();
        jGeneralPanel.setLayout(null);

        JLabel jProducer = new JLabel("Hersteller");
        JLabel jProductType = new JLabel("Produkttyp");
        JLabel jProductPrice = new JLabel("Preis");
        JLabel jAmount = new JLabel("Menge");

        Font font = jProducer.getFont();

        jProducer.setBounds(10,50, 100,20);
        jProductType.setBounds(200,50, 100,20);
        jProductPrice.setBounds(10,100, 100,20);
        jAmount.setBounds(200,100, 100,20);

        jProductNameF = new JLabel("Produktdetails");
        jProducerF = new JLabel("");
        jProductTypeF = new JLabel("");
        jProductPriceF = new JLabel("");
        jProductAmountF = new JLabel("");
        jProductNameF.setHorizontalAlignment(SwingConstants.CENTER);

        jProductNameF.setFont(font.deriveFont(font.getStyle() & Font.BOLD));
        jProducerF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
        jProductTypeF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
        jProductPriceF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
        jProductAmountF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));

        jProducerF.setBounds(110,50, 90,20);
        jProductTypeF.setBounds(300,50, 100,20);
        jProductPriceF.setBounds(110,100, 100,20);
        jProductAmountF.setBounds(300,100, 100,20);

        jGeneralPanel.add(jProducer);
        jGeneralPanel.add(jProductType);
        jGeneralPanel.add(jProductPrice);
        jGeneralPanel.add(jAmount);
        jGeneralPanel.add(jProducerF);
        jGeneralPanel.add(jProductTypeF);
        jGeneralPanel.add(jProductPriceF);
        jGeneralPanel.add(jProductAmountF);

        jNutrition = new JLabel("Nährwerte");
        jNutrition.setFont(font.deriveFont(font.getStyle() & Font.BOLD));
        jNutrition.setBounds((JConstants.WINDOW_SIZE_X/2-100)/2, JConstants.WINDOW_SIZE_Y/2-30, 100,20);

        jGeneralPanel.add(jNutrition);
        add(jGeneralPanel);
        add(jProductNameF, BorderLayout.PAGE_START);

        jCalories = new JLabel("Kalorien");
        jFat = new JLabel("Fett");
        jCarbos = new JLabel("Kohlenhydrate");

        jCalories.setBounds(10,280, 100,20);
        jFat.setBounds(200,280, 100,20);
        jCarbos.setBounds(10,320, 120,20);
        jGeneralPanel.add(jCalories);
        jGeneralPanel.add(jFat);
        jGeneralPanel.add(jCarbos);

        jFatF = new JLabel("");
        jCarbosF = new JLabel("");
        jCaloriesF = new JLabel("");

        jCaloriesF.setBounds(110,280, 100,20);
        jFatF.setBounds(300,280, 100,20);
        jCarbosF.setBounds(140,320, 120,20);

        jCaloriesF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
        jFatF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
        jCarbosF.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));

        jGeneralPanel.add(jCaloriesF);
        jGeneralPanel.add(jFatF);
        jGeneralPanel.add(jCarbosF);

        enableNutritionalValues(false);
    }

    public void setLabelSize(JLabel jLabel, Dimension d){
        jLabel.setMinimumSize(d);
        jLabel.setPreferredSize(d);
        jLabel.setMaximumSize(d);
    }

    public void setProductDetails(String name, String producer, String type, String price, String amount){
        jProductNameF.setText(name);
        jProducerF.setText(producer);
        jProductTypeF.setText(type);
        jProductPriceF.setText(price);
        jProductAmountF.setText(amount);
    }

    public void setNutritionalValues(String calories, String fat, String carbos){
        jCaloriesF.setText(calories);
        jFatF.setText(fat);
        jCarbosF.setText(carbos);
    }

    public void enableNutritionalValues(boolean enable){
        if(enable && !jCaloriesF.isVisible()){
            jCaloriesF.setVisible(true);
            jFatF.setVisible(true);
            jCarbosF.setVisible(true);
            jCalories.setVisible(true);
            jFat.setVisible(true);
            jCarbos.setVisible(true);
            jNutrition.setVisible(true);
        } else  {
            jCaloriesF.setVisible(false);
            jFatF.setVisible(false);
            jCarbosF.setVisible(false);
            jCalories.setVisible(false);
            jFat.setVisible(false);
            jCarbos.setVisible(false);
            jNutrition.setVisible(false);
        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,JConstants.WINDOW_SIZE_Y/2-20,JConstants.WINDOW_SIZE_X,JConstants.WINDOW_SIZE_Y/2-20);
    }
}
