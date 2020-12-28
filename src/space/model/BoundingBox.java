package space.model;

public class BoundingBox {
    public final Vector2D lowerLeft;
    public final Vector2D upperRight;

    public BoundingBox(Vector2D lowerLeft, Vector2D upperRight){
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }
}
