package space.view.panels;

import space.Translations;
import space.objects.rocketParts.*;
import space.status.GameData;

import javax.swing.*;
import java.awt.*;

public class UpgradesPanel extends JPanel {
    private GameData gameData;
    JLabel [] upgradeLabels;
    JLabel [] costLabels;
    JButton [] upgradeButtons;

    JLabel moneyLabel;

    public UpgradesPanel(GameData gameData){
        this.gameData = gameData;
        genVisual();
        genLayout();
    }

    public void reset(GameData gameData){
        this.gameData = gameData;
        removeAll();
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
        setCost(0, Hull.upgrade_costs[gameData.hullLevel]);
        setCost(1, Engine.upgrade_costs[gameData.engineLevel]);
        setCost(2, Fins.upgrade_costs[gameData.finsLevel]);
        setCost(3, Nose.upgrade_costs[gameData.noseLevel]);
        setCost(4, FuelTank.upgrade_costs[gameData.tankLevel]);
        moneyLabel.setText(Translations.getTranslation("money") + ": " + gameData.money);
    }

    private void upgradeClicked(String upgradeName){
        switch (upgradeName) {
            case "hull" -> gameData.money -= Hull.upgrade_costs[gameData.hullLevel];
            case "engine" -> gameData.money -= Engine.upgrade_costs[gameData.engineLevel];
            case "fins" -> gameData.money -= Fins.upgrade_costs[gameData.finsLevel];
            case "nose" -> gameData.money -= Nose.upgrade_costs[gameData.noseLevel];
            case "tank" -> gameData.money -= FuelTank.upgrade_costs[gameData.tankLevel];
        }
        switch (upgradeName) {
            case "hull" -> gameData.hullLevel++;
            case "engine" -> gameData.engineLevel++;
            case "fins" -> gameData.finsLevel++;
            case "nose" -> gameData.noseLevel++;
            case "tank" -> gameData.tankLevel++;
        }
        updateCosts();
        gameData.refreshRequired = true;
    }

    private void genVisual(){
        String upgradeTr = Translations.getTranslation("upgrade");
        String[] upgradeNames = new String[]{
                "hull",
                "engine",
                "fins",
                "nose",
                "tank"
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
        gbc.weightx = 1;
        for(int i = 0; i < upgradeLabels.length; i++){
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
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
