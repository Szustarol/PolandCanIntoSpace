package space.view.panels;

import space.objects.AbstractGameObject;
import space.status.Runner;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class GamePanel extends JPanel {

    Runner runner = null;

    public void setRunner(Runner runner){
        this.runner = runner;
    }

    public void paint(Graphics graphics){
        if(runner == null)
            return;

        LinkedList<AbstractGameObject> objects = runner.getDrawableBetween(-100, 100);

        objects.forEach(abstractGameObject -> {
            graphics.drawImage(abstractGameObject.getImage(0), (int)abstractGameObject.getPosition().x, (int)abstractGameObject.getPosition().y, this);


        });

    }
}
