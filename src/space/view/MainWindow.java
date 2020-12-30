package space.view;

import space.status.GameData;
import space.status.Runner;
import space.view.panels.GamePanel;
import space.view.panels.GameStatPanel;
import space.view.panels.UpgradesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

    private UpgradesPanel upgradesPanel;
    private GamePanel gamePanel;
    private GameStatPanel gameStatPanel;
    private Timer repaintTimer;
    private Runner runner;
    private GameData gameData;

    private void initPanelsAndLayout(){
        setLayout(new BorderLayout());
        upgradesPanel = new UpgradesPanel();
        gamePanel = new GamePanel();
        gameStatPanel = new GameStatPanel();
        add(upgradesPanel, BorderLayout.LINE_START);
        add(gamePanel, BorderLayout.CENTER);
        add(gameStatPanel, BorderLayout.PAGE_END);
        gamePanel.setVisible(true);
        gameStatPanel.setVisible(true);
        gamePanel.setGameStatPanel(gameStatPanel);
    }

    private void repaintHandler(ActionEvent e){
        if(runner != null && runner.isStarted())
            gamePanel.repaint();
        if(runner != null && runner.isGameFinished()){
            runner = new Runner(gameData);
            gamePanel.setRunner(runner);
        }
    }

    private void initControls(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public MainWindow(String title){
        setTitle(title);
        initPanelsAndLayout();
        initControls();

        gameData = new GameData(0, 0, 0,0, 0,0);

        runner = new Runner(gameData);

        repaintTimer = new Timer(1000/30, this::repaintHandler);
        repaintTimer.start();

        gamePanel.setRunner(runner);
        gamePanel.repaint();
    }

}
