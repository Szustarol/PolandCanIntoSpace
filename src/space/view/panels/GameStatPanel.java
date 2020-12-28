package space.view.panels;

import space.Translations;

import javax.swing.*;

public class GameStatPanel extends JPanel {
    JLabel rocketHeightLabel;

    public void setRocketHeight(double height){
        rocketHeightLabel.setText(Translations.getTranslation("rocket_height") + ": " + height);
    }

    public GameStatPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        rocketHeightLabel = new JLabel();
        rocketHeightLabel.setVisible(true);
        setRocketHeight(0);
        add(rocketHeightLabel);
    }
}
