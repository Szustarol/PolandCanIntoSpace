package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.rocketParts.Engine;
import space.objects.rocketParts.Hull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Rocket extends AbstractGameObject {

    private Hull hull;
    private Engine engine;

    private double fuelRemaining;

    private double maxFuel;//change to engine later

    private double getWeight(){
        return hull.partWeight() + engine.partWeight();
    }

    public void applyDrag(double deltaTime){
        double coef = 0.2; //nose.getcoef, fins.getcoef;
        double constCoef = 0.1;
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
        int w = 180;
        int h = 250;
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(engine.getImage(), 0, 0, null);
        g.drawImage(hull.getImage(), 0, 0, null);

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
        fuelRemaining = 0;
    }

    public void addFuel(double fuel){
        fuelRemaining += fuel;
        if(fuelRemaining > maxFuel){
            fuelRemaining = maxFuel;
        }
        if(fuelRemaining < 0)
            fuelRemaining = 0;
    }

    @Override
    public BoundingBox getHitBox() {
        return null;
    }

    @Override
    public void interact(AbstractGameObject another) {
        //do nothing
    }
}
