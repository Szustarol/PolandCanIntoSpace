package space.status;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.AbstractGameObject;
import space.objects.BoostArrow;
import space.objects.Coin;
import space.objects.Rocket;
import space.objects.objectTypes.BoostType;
import space.objects.objectTypes.CoinType;
import space.objects.staticObjects.Cloud;
import space.objects.staticObjects.Ground;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.util.*;

public class Runner {

    private Map map;
    private TreeSet<AbstractGameObject> gameObjectSet;
    private Rocket rocket;
    private boolean started = false;
    private Timer gameTimer = null;
    private double prevTime = -1;
    private boolean gameFinished = false;
    private double mapWidth = 7000;
    private GameData gameData;
    private Random generator;

    public boolean throttleUp = false;
    public int horizontalThrottlePosition = 0;

    public Runner(GameData gameData){
        this.gameData = gameData;

        generator = new Random();

        map = new Map(mapWidth);

        gameObjectSet = new TreeSet<>();

        Ground ground = new Ground(new Vector2D(0, -400));
        map.addObject(ground);
        gameObjectSet.add(ground);

        rocket= new Rocket();
        rocket.setData(gameData);
        rocket.addObserver(map);
        gameObjectSet.add(rocket);
        map.addObject(rocket);
        generateClouds(1000);
        generateCoins(1500);
        generateBoost(500);
    }

    public void reloadData(){
        rocket.setData(gameData);
    }

    private void generateClouds(int nClouds){
        for(int i = 0; i < nClouds; i++){
            Vector2D cloudPosition = Vector2D.randomVector(-mapWidth/2-500, mapWidth/2+500, 100, 100000);
            Cloud cloud = new Cloud(cloudPosition);
            gameObjectSet.add(cloud);
            map.addObject(cloud);
        }
    }

    private double[] generateProbabilities(int nTypes){
        int probSum = nTypes*(nTypes+1)/2;
        double[] prob = new double[nTypes];
        for(int i = 0; i < nTypes; i++){
            prob[i] = ((double) nTypes-i)/probSum;
            if(i > 0)
                prob[i] += prob[i-1];
        }
        return prob;
    }

    private int getProbabilityIndex(double generated, double[] probs){
        int idx = 0;
        while(probs[idx] < generated)
            idx++;
        return idx;
    }

    private Vector2D randomValidMapVector(){
        return Vector2D.randomVector(-mapWidth/2-500, mapWidth/2+500, 100, 100000);
    }

    private void generateBoost(int nBoosts){
        double[] probs = generateProbabilities(BoostType.values().length);
        for(int i = 0; i < nBoosts; i++){
            double gen = generator.nextDouble();
            int idx = getProbabilityIndex(gen, probs);
            BoostType newBoostType = BoostType.values()[idx];
            Vector2D boostPosition = randomValidMapVector();
            BoostArrow boost = new BoostArrow(newBoostType, boostPosition);
            gameObjectSet.add(boost);
            map.addObject(boost);
        }

    }

    private void generateCoins(int nCoins){
        double[] probs = generateProbabilities(CoinType.values().length);
        for(int i = 0; i < nCoins; i++){
            double gen = generator.nextDouble();
            int idx = getProbabilityIndex(gen, probs);
            CoinType newCoinType = CoinType.values()[idx];
            Vector2D coinPosition = randomValidMapVector();
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

        makeObjectsInteract();

        moveObjects(deltaTime);

        handleRocketEngine(deltaTime);

        deleteDestroyedObjects();
    }

    private void makeObjectsInteract(){
        //right now only rocket-else interaction matters
        double searchTo = rocket.getHitBox().upperRight.y+100;
        double searchFrom = rocket.getHitBox().lowerLeft.y-100;
        ArrayList<AbstractGameObject> possibleCollisions =
                new ArrayList<>(map.getObjectsBetween(searchFrom, searchTo));
        ArrayList<BoundingBox> hitBoxes = new ArrayList<>(possibleCollisions.size());

        for (AbstractGameObject possibleCollision : possibleCollisions) {
            hitBoxes.add(possibleCollision.getHitBox());
        }

        for(int i = 0; i < possibleCollisions.size(); i++){
            AbstractGameObject object1 = possibleCollisions.get(i);
            BoundingBox object1HitBox = hitBoxes.get(i);
            if(object1HitBox == null)
                continue;
            for(int j = 0; j < possibleCollisions.size(); j++){
                if(i == j)
                    continue;
                AbstractGameObject object2 = possibleCollisions.get(j);
                BoundingBox object2HitBox = hitBoxes.get(j);
                if(object2HitBox == null)
                    continue;
                if(object1HitBox.collides(object2HitBox)){
                    object1.interact(object2);
                }
            }
        }
    }

    private void moveObjects(double deltaTime){
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
    }

    private void handleRocketEngine(double deltaTime){
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
    }

    private void deleteDestroyedObjects(){
        LinkedList<AbstractGameObject> toDelete = new LinkedList<>();
        gameObjectSet.forEach(abstractGameObject -> {
            if(abstractGameObject.markedForDeletion){
                toDelete.add(abstractGameObject);
                map.removeObject(abstractGameObject);
            }
        });
        gameObjectSet.removeAll(toDelete);
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
