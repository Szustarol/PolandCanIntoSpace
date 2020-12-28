package space.view.panels;

import space.model.Vector2D;
import space.objects.AbstractGameObject;
import space.status.Runner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class GamePanel extends JPanel implements KeyListener {

    Runner runner = null;
    GameStatPanel gameStatPanel = null;

    public GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    public void setRunner(Runner runner){
        this.runner = runner;
    }

    public void setGameStatPanel(GameStatPanel gameStatPanel){this.gameStatPanel = gameStatPanel;};

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        repaint();
        System.out.println("Key pressed");
        switch(e.getKeyChar()){
            case ' ':
                if(runner != null){
                    runner.start();
                    System.out.println("starting runner");
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    private Vector2D transformCoordinates(Vector2D reference, Vector2D position){
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        Vector2D absolute = position.sub(reference);
        absolute = new Vector2D(absolute.x, -absolute.y);
        absolute = absolute.add(new Vector2D(screenWidth/2, screenHeight/2));
        return absolute;
    }

    public void paint(Graphics graphics){
        super.paint(graphics);
        if(runner == null)
            return;

        Vector2D referencePosition = runner.getReferencePosition();

        LinkedList<AbstractGameObject> objects = runner.getDrawableBetween(referencePosition.y-1000, referencePosition.y+1000);

        if(gameStatPanel != null){
            gameStatPanel.setRocketHeight(referencePosition.y);
        }

        objects.forEach(abstractGameObject -> {
            BufferedImage image = abstractGameObject.getImage(0);
            Vector2D imageDimension = new Vector2D(image.getWidth(), image.getHeight()).scalarMul(0.5);
            Vector2D position = transformCoordinates(referencePosition, abstractGameObject.getPosition());
            position = position.sub(imageDimension);

            graphics.drawImage(image, (int)position.x, (int)position.y, this);
        });

    }
}
