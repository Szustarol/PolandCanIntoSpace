package space.view;

import space.view.panels.GamePanel;
import space.view.panels.UpgradesPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private UpgradesPanel upgradesPanel;
    private GamePanel gamePanel;

    private void initPanelsAndLayout(){
        setLayout(new BorderLayout());
        upgradesPanel = new UpgradesPanel();
        gamePanel = new GamePanel();
        add(upgradesPanel, BorderLayout.LINE_START);
        add(gamePanel, BorderLayout.CENTER);
    }

    private void initControls(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public MainWindow(String title){
        setTitle(title);
        initPanelsAndLayout();
        initControls();
    }

}
