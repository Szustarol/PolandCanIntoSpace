package space.view.panels;

import space.Translations;
import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.AbstractGameObject;
import space.objects.GameObjectType;
import space.objects.staticObjects.StaticGameObject;
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

    public void setGameStatPanel(GameStatPanel gameStatPanel){this.gameStatPanel = gameStatPanel;}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        repaint();
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
        switch (e.getKeyCode()) {
            case 38 -> runner.throttleUp = false;
            case 37, 39 -> runner.horizontalThrottlePosition = 0;
        }
    }


    private Vector2D transformCoordinates(Vector2D reference, Vector2D position){
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        Vector2D absolute = position.sub(reference);
        absolute = new Vector2D(absolute.x, -absolute.y);
        absolute = absolute.add(new Vector2D((double)screenWidth/2, (double)screenHeight/2));
        return absolute;
    }

    public Color getColorAtHeight(double height){
        double start = 0;
        double end = 100000;
        if(height > end)
            height = end;
        if(height < start)
            height = start;
        double percentage = height/(end-start);
        double startR = 55, startG = 192, startB = 235;
        double endR = 22, endG = 42, endB = 97;
        double diffR = endR-startR, diffG = endG-startG, diffB = endB-startB;
        return new Color(
                (int)(startR + diffR*percentage),
                (int)(startG + diffG*percentage),
                (int)(startB + diffB*percentage)
        );
    }

    public void paint(Graphics graphics){
        super.paint(graphics);
        if(runner == null)
            return;

        Vector2D referencePosition = runner.getReferencePosition();

        graphics.setColor(getColorAtHeight(referencePosition.y));
        graphics.fillRect(0, 0, getWidth(), getHeight());

        double margin  = 1000;
        LinkedList<AbstractGameObject> objects = runner.getDrawableBetween(
                referencePosition.y-(double)getHeight()/2-margin, referencePosition.y+(double)getHeight()/2+margin);

        gameStatPanel.updateWithRocketData(runner.getRocketStatus());

        objects.forEach(abstractGameObject -> {
            BufferedImage image = abstractGameObject.getImage(0);
            Vector2D imageDimension = new Vector2D(image.getWidth(), image.getHeight()).scalarMul(0.5);
            Vector2D objectPosition = abstractGameObject.getPosition();
            if(abstractGameObject.getType() == GameObjectType.STATIC){
                StaticGameObject go = (StaticGameObject) abstractGameObject;
                if(go.clingToPosition() != null){
                    objectPosition = new Vector2D(referencePosition.x, objectPosition.y);
                }
            }
//            BoundingBox bb = abstractGameObject.getHitBox();
//            if(bb != null){
//                double width = bb.upperRight.x - bb.lowerLeft.x;
//                double height = bb.upperRight.y - bb.lowerLeft.y;
//                Vector2D cd = new Vector2D(bb.lowerLeft.x, bb.upperRight.y);
//                cd = transformCoordinates(referencePosition, runner.targetPositionMapping(cd));
//                graphics.setColor(Color.red);
//                graphics.drawRect((int)cd.x, (int)cd.y, (int)width, (int)height);
//            }
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
        if(!runner.isStarted()){
            graphics.setColor(new Color(200, 100 ,0));
            Font font = graphics.getFont();
            graphics.setFont(font.deriveFont(18.0f));
            graphics.drawString(Translations.getTranslation("start_flight"), 10, 20);
        }
    }
}
