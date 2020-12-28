package space.status;

import space.model.Vector2D;
import space.objects.AbstractGameObject;
import space.objects.Rocket;
import space.objects.staticObjects.Cloud;
import space.objects.staticObjects.Ground;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

public class Runner {

    private Map map;
    private TreeSet<AbstractGameObject> gameObjectSet;
    private Rocket rocket;
    private boolean started = false;
    private Timer gameTimer = null;
    private double prevTime = -1;

    public Runner(){
        map = new Map(2000);

        gameObjectSet = new TreeSet<>();

        Ground ground = new Ground(new Vector2D(0, -400));
        map.addObject(ground);
        gameObjectSet.add(ground);

        rocket= new Rocket();
        gameObjectSet.add(rocket);
        map.addObject(rocket);

        for(int i = 0; i < 1000; i++){
            Vector2D cloudPosition = Vector2D.randomVector(-1000, 1000, 0, 20000);
            Cloud cloud = new Cloud(cloudPosition);
            gameObjectSet.add(cloud);
            map.addObject(cloud);
        }


    }

    private void timeStep(ActionEvent e){
        double deltaTime;
        if(prevTime == -1) {
            deltaTime = 0;
            prevTime = System.nanoTime();
        }
        else{
            deltaTime = System.nanoTime()-prevTime;
            prevTime = System.nanoTime();
        }
        deltaTime /= 1e8;
        final double finalTime = deltaTime;
        gameObjectSet.forEach(gameObject -> {
            if(gameObject.affectedByGravity)
                gameObject.accelerate(new Vector2D(0, -10), finalTime);
            gameObject.applyDrag(finalTime);
            gameObject.move(finalTime, map);
        });
        System.out.println("Rocket height: " + rocket.getPosition().y);

    }

    public Vector2D getReferencePosition(){
        return rocket.getPosition();
    }

    public void start(){
        if(gameTimer != null){
            gameTimer.stop();
        }
        gameTimer = new Timer(10, this::timeStep);
        prevTime = -1;
        rocket.accelerate(new Vector2D(0, 100), 1);
        gameTimer.start();
        started = true;
    }

    public boolean isStarted(){
        return started;
    }

    public LinkedList<AbstractGameObject> getDrawableBetween(double ystart, double yend){
        return map.getObjectsBetween(ystart, yend);
    }


}
