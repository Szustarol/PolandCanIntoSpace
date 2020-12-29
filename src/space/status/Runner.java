package space.status;

import space.model.Vector2D;
import space.objects.AbstractGameObject;
import space.objects.Coin;
import space.objects.Rocket;
import space.objects.objectTypes.CoinType;
import space.objects.staticObjects.Cloud;
import space.objects.staticObjects.Ground;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
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
    private boolean gameFinished = false;

    public boolean throttleUp = false;
    public int horizontalThrottlePosition = 0;

    public Runner(){
        map = new Map(2000);

        gameObjectSet = new TreeSet<>();

        Ground ground = new Ground(new Vector2D(0, -400));
        map.addObject(ground);
        gameObjectSet.add(ground);

        rocket= new Rocket();
        rocket.addObserver(map);
        gameObjectSet.add(rocket);
        map.addObject(rocket);

        generateClouds(1000);
        generateCoins(1500);
    }

    private void generateClouds(int nClouds){
        for(int i = 0; i < nClouds; i++){
            Vector2D cloudPosition = Vector2D.randomVector(-2000, 2000, 100, 100000);
            Cloud cloud = new Cloud(cloudPosition);
            gameObjectSet.add(cloud);
            map.addObject(cloud);
        }
    }

    private void generateCoins(int nCoins){
        int nTypes = CoinType.values().length;
        System.out.println(Arrays.toString(CoinType.values()));
        int probSum = nTypes*(nTypes+1)/2;
        double[] probs = new double[nTypes];
        Random generator = new Random();
        for(int i = 0; i < nTypes; i++){
            probs[i] = ((double)nTypes-i)/probSum;
            if(i > 0)
                probs[i] += probs[i-1];
        }
        System.out.println(Arrays.toString(probs));
        for(int i = 0; i < nCoins; i++){
            double gen = generator.nextDouble();
            int idx = 0;
            while(probs[idx] < gen){
                idx++;
            }
            CoinType newCoinType = CoinType.values()[idx];
            Vector2D coinPosition = Vector2D.randomVector(-2000, 2000, 100, 100000);
            Coin coin = new Coin(newCoinType, coinPosition);
            gameObjectSet.add(coin);
            map.addObject(coin);
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
        if(rocket.getPosition().y < 0){
            gameTimer.stop();
            gameFinished = true;
            started = false;
        }

        if(rocket.getFuelRemaining() > 0){
            Vector2D throttle = new Vector2D(0, 0);
            if(throttleUp){
                throttle = throttle.add(new Vector2D(0, 1));
            }
            if(horizontalThrottlePosition != 0){
                throttle = throttle.add(new Vector2D(horizontalThrottlePosition, 0));
            }
            if(throttle.x != 0 || throttle.y != 0)
                rocket.engineRun(throttle, deltaTime);
        }

        System.out.println(rocket.getPosition());
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
        rocket.accelerate(new Vector2D(0, 1000), 1);
        gameTimer.start();
        started = true;
    }

    public boolean isStarted(){
        return started;
    }

    public LinkedList<AbstractGameObject> getDrawableBetween(double ystart, double yend){
        return map.getObjectsBetween(ystart, yend);
    }

    public boolean isGameFinished(){
        return gameFinished;
    }

    public Vector2D targetPositionMapping(Vector2D vec){
        return map.targetPositionMapping(vec);
    }

    public RocketStatus getRocketStatus(){
        RocketStatus rocketStatus = new RocketStatus();
        rocketStatus.height = rocket.getPosition().y;
        rocketStatus.fuelLeft = rocket.getFuelRemaining();
        return rocketStatus;
    }

}
