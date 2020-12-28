package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.objectTypes.BoostType;

public class BoostArrow extends AbstractGameObject{

    private final Vector2D boostAcceleration;

    public BoostArrow(BoostType type){
        super(new Vector2D(0, 0), false);
        boostAcceleration = type.value();
        gameObjectType = GameObjectType.BOOST;
    }

    @Override
    public BoundingBox getHitBox() {
        return null;
    }

    @Override
    public void interact(AbstractGameObject another) {
        if(another.gameObjectType == GameObjectType.ROCKET){
            Rocket rocket = (Rocket) another;
            rocket.accelerate(boostAcceleration, 1);
        }
    }
}
