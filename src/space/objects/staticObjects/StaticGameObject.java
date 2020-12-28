package space.objects.staticObjects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.AbstractGameObject;

public class StaticGameObject extends AbstractGameObject {
    public StaticGameObject(Vector2D position) {
        super(position, false);
    }

    @Override
    public BoundingBox getHitBox() {
        return null;
    }

    @Override
    public void interact(AbstractGameObject another) {

    }
}
