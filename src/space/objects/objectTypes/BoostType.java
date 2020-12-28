package space.objects.objectTypes;

import space.model.Vector2D;

public enum BoostType {
    SMALL,
    MEDIUM,
    LARGE;

    public Vector2D value(){
        return switch (this) {
            case SMALL -> new Vector2D(0, 0.01);
            case MEDIUM -> new Vector2D(0, 0.1);
            case LARGE -> new Vector2D(0, 1);
        };
    }
}
