package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;

public abstract class AbstractGameObject {

    protected Vector2D position;
    protected Vector2D velocity;

    public final boolean affectedByGravity;

    protected GameObjectType gameObjectType;

    public AbstractGameObject(Vector2D position, boolean affectedByGravity){
        gameObjectType = GameObjectType.NONE;
        this.position = new Vector2D(position);
        velocity = new Vector2D(0, 0);
        this.affectedByGravity = affectedByGravity;
    }

    public Vector2D getPosition(){
        return new Vector2D(this.position);
    }

    public void accelerate(Vector2D acceleration, double deltaTime){
        velocity = velocity.add(acceleration.scalarMul(deltaTime));
    }

    public void move(double deltaTime){
        position.add(velocity.scalarMul(deltaTime));
    }

    public abstract BoundingBox getHitBox();

    public abstract void interact(AbstractGameObject another);

}
