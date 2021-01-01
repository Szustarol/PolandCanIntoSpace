package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.rocketParts.*;
import space.status.GameData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;


public class Rocket extends AbstractGameObject {

    private Hull hull;
    private Engine engine;
    private FuelTank fuelTank;
    private Fins fins;
    private GameData gameData;
    private Nose nose;

    private double getWeight(){
        return hull.partWeight() + engine.partWeight() + fins.partWeight();
    }

    public void setData(GameData gameData){
        engine = new Engine(gameData.engineLevel);
        hull = new Hull(gameData.hullLevel);
        fuelTank = new FuelTank(gameData.tankLevel);
        fins = new Fins(gameData.finsLevel);
        nose = new Nose(gameData.noseLevel);
        this.gameData = gameData;
    }

    public void applyDrag(double deltaTime){
        double coef = 0.2 * fins.verticalDragCoefficient;
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
        int w = hull.getImage().getWidth();
        int h = hull.getImage().getHeight()+engine.getImage().getHeight();
        int notFinsOffset = 0;
        int notTopOffset = 0;
        int notNoseOffset = 0;
        if(fins.getImage() != null){
            notFinsOffset = 40;
            w = fins.getImage().getWidth();
        }
        if(nose.getImage() != null){
            notNoseOffset = 10;
            notTopOffset = (int)(nose.getImage().getHeight()*0.7);
            w = Math.max(w, nose.getImage().getWidth());
            h += (int)(nose.getImage().getHeight());
        }
        if(nose.getImage() != null && fins.getImage() != null){
            w += 10;
        }
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(engine.getImage(), notFinsOffset+notNoseOffset, notTopOffset+135, null);
        g.drawImage(hull.getImage(), notFinsOffset+notNoseOffset, notTopOffset, null);
        if(fins.getImage() != null){
            g.drawImage(fins.getImage(), notNoseOffset, notTopOffset+50, null);
        }
        if(nose.getImage() != null){
            g.drawImage(nose.getImage(), notFinsOffset, 0, null);
        }

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
        acceleration = new Vector2D(acceleration.x, acceleration.y*fins.horizontalCoefficient);
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
