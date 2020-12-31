package space.view.panels;

import space.Translations;
import space.objects.rocketParts.Engine;
import space.objects.rocketParts.Hull;
import space.status.GameData;

import javax.swing.*;
import java.awt.*;

public class UpgradesPanel extends JPanel {
    private final GameData gameData;
    JLabel [] upgradeLabels;
    JLabel [] costLabels;
    JButton [] upgradeButtons;

    JLabel moneyLabel;

    public UpgradesPanel(GameData gameData){
        this.gameData = gameData;
        genVisual();
        genLayout();
    }

    public void refresh(){
        updateCosts();
    }

    public void setClickable(boolean clickable){
        for(JButton button : upgradeButtons){
            button.setEnabled(clickable);
        }
    }

    private void setCost(int index, double cost){
        if(cost != -1){
            costLabels[index].setText(String.valueOf(cost));
            costLabels[index].setEnabled(!(cost > gameData.money));
            upgradeButtons[index].setEnabled(!(cost > gameData.money));
        }
        else{
            costLabels[index].setText("max");
            upgradeButtons[index].setVisible(false);
        }
    }

    private void updateCosts(){
        setCost(0, Hull.hull_upgrade_costs[gameData.hullLevel]);
        setCost(1, Engine.engine_upgrade_costs[gameData.engineLevel]);
        moneyLabel.setText(Translations.getTranslation("money") + ": " + gameData.money);
    }

    private void upgradeClicked(String upgradeName){
        switch (upgradeName) {
            case "hull" -> gameData.hullLevel++;
            case "engine" -> gameData.engineLevel++;
        }
        updateCosts();
        gameData.refreshRequired = true;
    }

    private void genVisual(){
        String upgradeTr = Translations.getTranslation("upgrade");
        String[] upgradeNames = new String[]{
                "hull",
                "engine"
        };

        upgradeLabels = new JLabel[upgradeNames.length];
        costLabels = new JLabel[upgradeNames.length];
        upgradeButtons = new JButton[upgradeNames.length];

        int idx = 0;
        for(String upgradeName : upgradeNames){
            upgradeLabels[idx] = new JLabel(upgradeTr + ": " + Translations.getTranslation(upgradeName));
            upgradeButtons[idx] = new JButton("+");
            upgradeButtons[idx].setFocusable(false);
            costLabels[idx] = new JLabel();
            upgradeLabels[idx].setVisible(true); upgradeButtons[idx].setVisible(true); costLabels[idx].setVisible(true);
            final int handlerIdx = idx;
            upgradeButtons[idx].addActionListener(e -> upgradeClicked(upgradeName));

            idx++;
        }
        moneyLabel = new JLabel();


        updateCosts();
    }

    private void genLayout(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 1, 1, 1);
        for(int i = 0; i < upgradeLabels.length; i++){
            gbc.gridx = 0;
            gbc.gridy = i;
            add(upgradeLabels[i], gbc);
            gbc.gridx = 1;
            add(upgradeButtons[i], gbc);
            gbc.gridx = 2;
            add(costLabels[i], gbc);
        }
        gbc.gridy++;
        gbc.gridx = 0;
        add(moneyLabel, gbc);
    }

}
