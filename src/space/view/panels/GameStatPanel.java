package space.view.panels;

import space.Translations;
import space.model.Vector2D;
import space.status.RocketStatus;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class GameStatPanel extends JPanel {
    JLabel rocketHeightLabel;
    JLabel rocketFuelLabel;
    JLabel maxHeightLabel;
    JLabel velocityLabel;

    private double maxHeight = -1;
    private final DecimalFormat decimalFormat;

    public void setRocketHeight(double height){
        rocketHeightLabel.setText(Translations.getTranslation("rocket_height") + ": " + decimalFormat.format(height));
    }

    public void setRocketFuel(double fuel){
        rocketFuelLabel.setText(Translations.getTranslation("rocket_fuel_left") + ": " + decimalFormat.format(fuel));
    }

    private void setMaxHeight(){
        maxHeightLabel.setText(Translations.getTranslation("max_reached_height") + ": " + decimalFormat.format(maxHeight));
    }

    private void setVelocity(Vector2D velocity){
        velocityLabel.setText(Translations.getTranslation("rocket_velocity") + ": " + velocity.niceRepresentation());
    }

    public void resetLabels(){
        setRocketHeight(0);
        setRocketFuel(0);
        maxHeight = 0;
        setMaxHeight();
        setVelocity(new Vector2D(0, 0));
    }


    private void initLabels(){
        rocketHeightLabel = new JLabel("", SwingConstants.LEFT);
        rocketFuelLabel = new JLabel("", SwingConstants.LEFT);
        maxHeightLabel = new JLabel("", SwingConstants.LEFT);
        velocityLabel = new JLabel("", SwingConstants.LEFT);
        rocketHeightLabel.setVisible(true);
        rocketFuelLabel.setVisible(true);
        maxHeightLabel.setVisible(true);
        velocityLabel.setVisible(true);
        resetLabels();
    }

    public void updateWithRocketData(RocketStatus rocketStatus){
        setRocketFuel(rocketStatus.fuelLeft);
        setRocketHeight(rocketStatus.height);
        if(rocketStatus.height > maxHeight){
            maxHeight = rocketStatus.height;
        }
        setMaxHeight();
        setVelocity(rocketStatus.speed);
    }

    public GameStatPanel(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        decimalFormat = new DecimalFormat("#.##");

        initLabels();
        gbc.insets = new Insets(2, 11, 2, 11);
        gbc.gridx = 0; gbc.gridy = 0;
        add(rocketHeightLabel, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        add(rocketFuelLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        add(maxHeightLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        add(velocityLabel, gbc);
    }
}
