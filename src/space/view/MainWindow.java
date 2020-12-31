package space.view;

import space.status.GameData;
import space.status.Runner;
import space.view.panels.GamePanel;
import space.view.panels.GameStatPanel;
import space.view.panels.UpgradesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements KeyListener {

    private UpgradesPanel upgradesPanel;
    private GamePanel gamePanel;
    private GameStatPanel gameStatPanel;
    private Timer repaintTimer;
    private Runner runner;
    private GameData gameData;

    private void initPanelsAndLayout(){
        setLayout(new BorderLayout());
        upgradesPanel = new UpgradesPanel(gameData);
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
        upgradesPanel.refresh();
        if(gameData.refreshRequired){
            gameData.refreshRequired = false;
            runner.reloadData();
            gamePanel.repaint();
        }
        if(runner != null && runner.isStarted()) {
            gamePanel.repaint();
            upgradesPanel.setClickable(false);
        }
        if(runner != null && runner.isGameFinished()){
            upgradesPanel.setClickable(true);
            runner = new Runner(gameData);
            gamePanel.setRunner(runner);
            gamePanel.repaint();
        }
    }

    private void initControls(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public MainWindow(String title){
        gameData = new GameData(0, 0, 0,0, 0,0);

        setTitle(title);
        initPanelsAndLayout();
        initControls();

        runner = new Runner(gameData);

        repaintTimer = new Timer(1000/30, this::repaintHandler);
        repaintTimer.start();

        gamePanel.setRunner(runner);
        gamePanel.setFocusable(true);
        gamePanel.repaint();
        gamePanel.requestFocus();
        gamePanel.requestFocusInWindow();
        gamePanel.grabFocus();

        setSize(new Dimension(800, 600));
    }


    //redirect all input
    @Override
    public void keyTyped(KeyEvent e) {
        gamePanel.requestFocus();
        gamePanel.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.requestFocus();
        gamePanel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gamePanel.requestFocus();
        gamePanel.keyReleased(e);
    }

}
