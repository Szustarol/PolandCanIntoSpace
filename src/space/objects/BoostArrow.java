package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.objectTypes.BoostType;

import java.awt.image.BufferedImage;

public class BoostArrow extends AbstractGameObject{

    private final Vector2D boostAcceleration;
    private BoostType type;

    public BoostArrow(BoostType type, Vector2D boostPosition){
        super(boostPosition, false);
        boostAcceleration = type.value();
        gameObjectType = GameObjectType.BOOST;
        this.type = type;
    }

    public BufferedImage getImage(float rotation){
        return type.getImage();
    }

    @Override
    public void interact(AbstractGameObject another) {
        if(another.gameObjectType == GameObjectType.ROCKET){
            Rocket rocket = (Rocket) another;
            rocket.accelerate(boostAcceleration, 1);
            this.markedForDeletion = true;
        }
    }
}
