package space.model;

public class BoundingBox {
    public final Vector2D lowerLeft;
    public final Vector2D upperRight;

    public BoundingBox(Vector2D lowerLeft, Vector2D upperRight){
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "lowerLeft=" + lowerLeft +
                ", upperRight=" + upperRight +
                '}';
    }

    public boolean collides(BoundingBox other){
        boolean xOverlap = lowerLeft.x <= other.upperRight.x
                && other.lowerLeft.x <= upperRight.x;

        boolean yOverlap = lowerLeft.y <= other.upperRight.y
                && other.lowerLeft.y <= upperRight.y;

        return xOverlap && yOverlap;
    }
}
