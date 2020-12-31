package space.view.panels;

import space.Translations;
import space.status.RocketStatus;

import javax.swing.*;
import java.text.DecimalFormat;

public class GameStatPanel extends JPanel {
    JLabel rocketHeightLabel;
    JLabel rocketFuelLabel;

    private DecimalFormat decimalFormat;

    public void setRocketHeight(double height){
        rocketHeightLabel.setText(Translations.getTranslation("rocket_height") + ": " + decimalFormat.format(height));
    }

    public void setRocketFuel(double fuel){
        rocketFuelLabel.setText(Translations.getTranslation("rocket_fuel_left") + ": " + decimalFormat.format(fuel));
    }

    public void resetLabels(){
        setRocketHeight(0);
        setRocketFuel(0);
    }


    private void initLabels(){
        rocketHeightLabel = new JLabel();
        rocketFuelLabel = new JLabel();
        rocketHeightLabel.setVisible(true);
        rocketFuelLabel.setVisible(true);
        resetLabels();
    }

    public void updateWithRocketData(RocketStatus rocketStatus){
        setRocketFuel(rocketStatus.fuelLeft);
        setRocketHeight(rocketStatus.height);
    }

    public GameStatPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        decimalFormat = new DecimalFormat("#.##");

        initLabels();

        add(rocketHeightLabel);
        add(rocketFuelLabel);
    }
}
