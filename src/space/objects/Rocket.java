package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.rocketParts.Engine;
import space.objects.rocketParts.FuelTank;
import space.objects.rocketParts.Hull;
import space.status.GameData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;


public class Rocket extends AbstractGameObject {

    private Hull hull;
    private Engine engine;
    private FuelTank fuelTank;
    private GameData gameData;

    private double getWeight(){
        return hull.partWeight() + engine.partWeight();
    }

    public void setData(GameData gameData){
        engine = new Engine(gameData.engineLevel);
        hull = new Hull(gameData.hullLevel);
        fuelTank = new FuelTank(gameData.tankLevel);
        this.gameData = gameData;
    }

    public void applyDrag(double deltaTime){
        double coef = 0.2; //nose.getcoef, fins.getcoef;
        double constCoef = 2;
        double forceCoef = constCoef*coef;
        double forceX = forceCoef* velocity.x* velocity.x;
        double forceY = forceCoef* velocity.y* velocity.y;
        if(velocity.y > 0)
            forceY *= -1;
        if(velocity.x > 0)
            forceX *= -1;
        Vector2D dragForce = new Vector2D(forceX, forceY);
        dragForce = dragForce.scalarMul(1/getWeight());
        velocity = velocity.add(dragForce.scalarMul(deltaTime));
    }

    public BufferedImage getImage(float rotation){
        int w = 150;
        int h = 250;
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(engine.getImage(), 20, 135, null);
        g.drawImage(hull.getImage(), 20, 0, null);

        g.dispose();

        if(rotation != 0){
            BufferedImage afterRotation = new BufferedImage(w, h, combined.getType());
            Graphics2D g2d = afterRotation.createGraphics();
            g2d.rotate(Math.toRadians(rotation), w/2, h/2);
            g2d.drawImage(combined, null, 0, 0);
            g2d.dispose();
            combined = afterRotation;
        }

        return combined;
    }

    public Rocket() {
        super(new Vector2D(0, 0), true);
        gameObjectType = GameObjectType.ROCKET;
        hull = new Hull(0);
        engine = new Engine(0);
        fuelTank = new FuelTank(0);
    }

    public void addFuel(double fuel){
        fuelTank.addFuel(fuel);
    }

    public double getFuelRemaining(){
        return fuelTank.getFuel();
    }

    public void engineRun(Vector2D direction, double deltaTime){
        double force = engine.force*20;
        Vector2D acceleration = direction.scalarMul(force).scalarMul(1/getWeight());
        accelerate(acceleration, deltaTime);
        fuelTank.used(deltaTime);
    }


    @Override
    public void interact(AbstractGameObject another) {
        if(another.gameObjectType == GameObjectType.COIN){
            another.markedForDeletion = true;
            Coin coin = (Coin)another;
            if(this.gameData != null)
                gameData.money += coin.getValue();
        }
    }
}
