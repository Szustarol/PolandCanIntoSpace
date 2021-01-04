package space.model;

import java.text.DecimalFormat;
import java.util.Random;

public class Vector2D implements Comparable<Vector2D>{
    public final double x;
    public final double y;
    public static Random generator = new Random();
    private static final DecimalFormat niceDecimalFormat = new DecimalFormat("#.##");

    public static Vector2D randomVector(double fromX, double toX, double fromY, double toY){
        double x = generator.nextDouble();
        double y = generator.nextDouble();

        x = (toX - fromX)*x + fromX;
        y = (toY - fromY)*y + fromY;

        return new Vector2D(x, y);
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other){
        this.x = other.x;
        this.y = other.y;
    }

    public String niceRepresentation(){
        return "(" + niceDecimalFormat.format(x) + ", " + niceDecimalFormat.format(y) + ")";
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(this.x + other.x, this.y+other.y);
    }

    public Vector2D sub(Vector2D other) {return new Vector2D(this.x-other.x, this.y-other.y);}

    public Vector2D scalarMul(double value){
        return new Vector2D(this.x * value, this.y * value);
    }

    public boolean equals(Vector2D other){
        return this.x == other.x && this.y == other.y;
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
