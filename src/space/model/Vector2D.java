package space.model;

public class Vector2D implements Comparable<Vector2D>{
    public final double x;
    public final double y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other){
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(this.x + other.x, this.y+other.y);
    }

    public Vector2D scalarMul(double value){
        return new Vector2D(this.x * value, this.y * value);
    }

    public int compareTo(Vector2D other){
        if (this.y == other.y){
            if(this.x > other.x)
                return 1;
            else if(this.x < other.x)
                return -1;
            return 0;
        }
        else{
            if(this.y > other.y)
                return 1;
            return -1;
        }
    }
}
