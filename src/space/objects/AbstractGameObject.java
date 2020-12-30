package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.staticObjects.StaticGameObject;
import space.status.IPositionChangeObserver;
import space.status.Map;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class AbstractGameObject implements Comparable<AbstractGameObject>{

    protected Vector2D position;
    protected Vector2D velocity;

    protected LinkedList<IPositionChangeObserver> observers = null;

    public final boolean affectedByGravity;

    protected GameObjectType gameObjectType;

    private static int newObjectID = 0;

    public final int objectID;

    public boolean markedForDeletion = false;

    public void addObserver(IPositionChangeObserver observer){
        if(observers == null)
            observers = new LinkedList<>();
        observers.add(observer);
    }

    public AbstractGameObject(Vector2D position, boolean affectedByGravity){
        objectID = newObjectID;
        newObjectID++;
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

    public void applyDrag(double deltaTime){

    }

    public void move(double deltaTime, Map map){
        Vector2D prevPos = new Vector2D(position);
        position = position.add(velocity.scalarMul(deltaTime));
        position = map.targetPositionMapping(position);
        if(observers != null){
            observers.forEach(observer -> {
                observer.positionChanged(this, prevPos, position);
            });
        }
    }

    public BoundingBox getHitBox(){
        Vector2D reference = getPosition();
        BufferedImage img = getImage(0);
        if(img == null)
            return null;
        Vector2D lowerLeft = reference.sub(
                new Vector2D(
                        (double)img.getWidth()/2,
                        (double)img.getWidth()/2
                )
        );
        Vector2D upperRight = reference.add(
                new Vector2D(
                        (double)img.getWidth()/2,
                        (double)img.getHeight()/2
                )
        );
        return new BoundingBox(
                lowerLeft, upperRight
        );
    }

    public abstract void interact(AbstractGameObject another);

    public int compareTo(AbstractGameObject another){
        return another.objectID - this.objectID;
    }

    public BufferedImage getImage(float rotation){
        return null;
    }

}
