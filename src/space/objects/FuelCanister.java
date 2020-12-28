package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;

public class FuelCanister extends AbstractGameObject{

    private double fuelValue;

    public FuelCanister(){
        super(new Vector2D(0, 0), false);
        gameObjectType = GameObjectType.CANISTER;
    }

    public void setFuelValue(double fuelValue){
        this.fuelValue = fuelValue;
    }

    @Override
    public BoundingBox getHitBox() {
        return null;
    }

    @Override
    public void interact(AbstractGameObject another) {
        //do nothing
        if (another.gameObjectType == GameObjectType.ROCKET) {
            Rocket rocket = (Rocket) another;
            rocket.addFuel(fuelValue);
        }
    }
}
