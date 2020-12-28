package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.rocketParts.Engine;
import space.objects.rocketParts.Hull;

public class Rocket extends AbstractGameObject {

    private Hull hull;
    private Engine engine;

    private double fuelRemaining;

    private double maxFuel;//change to engine later

    public Rocket() {
        super(new Vector2D(0, 0), true);
        gameObjectType = GameObjectType.ROCKET;
        hull = new Hull();
        engine = new Engine();
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
