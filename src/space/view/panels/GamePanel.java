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
        System.out.println(e.getKeyCode());
        switch(e.getKeyCode()){
            case 32:
                if(runner != null && !runner.isStarted() && !runner.isGameFinished()){
                    runner.start();
                    System.out.println("starting runner");
                }
                break;
            case 37:
                if(runner != null)
                    runner.horizontalThrottlePosition = -1;
                break;
            case 39:
                if(runner != null)
                    runner.horizontalThrottlePosition = 1;
                break;
            case 38:
                if(runner != null)
                    runner.throttleUp = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        repaint();
        switch (e.getKeyCode()){
            case 38:
                runner.throttleUp = false;
                break;
            case 37:
            case 39:
                runner.horizontalThrottlePosition = 0;
                break;
        }
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

        gameStatPanel.updateWithRocketData(runner.getRocketStatus());

        objects.forEach(abstractGameObject -> {
            BufferedImage image = abstractGameObject.getImage(0);
            Vector2D imageDimension = new Vector2D(image.getWidth(), image.getHeight()).scalarMul(0.5);
            Vector2D objectPosition = abstractGameObject.getPosition();
            if(!runner.targetPositionMapping(objectPosition).equals(objectPosition)){
                //draw two instances
                Vector2D position2 = transformCoordinates(referencePosition, runner.targetPositionMapping(objectPosition));
                position2 = position2.sub(imageDimension);
                graphics.drawImage(image, (int)position2.x, (int)position2.y, this);

            }

            Vector2D position = transformCoordinates(referencePosition, objectPosition);
            position = position.sub(imageDimension);

            graphics.drawImage(image, (int)position.x, (int)position.y, this);
        });

    }
}
