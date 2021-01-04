package space.objects.staticObjects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.AbstractGameObject;
import space.objects.GameObjectType;
import space.status.Map;

public class StaticGameObject extends AbstractGameObject {
    public StaticGameObject(Vector2D position) {
        super(position, false);
        gameObjectType = GameObjectType.STATIC;
    }

    public Vector2D clingToPosition(){
        return null;
    }

    @Override
    public BoundingBox getHitBox() {
        return null;
    }

    @Override
    public void interact(AbstractGameObject another) {

    }

    @Override
    public void move(double deltaTime, Map map){
    }

}
