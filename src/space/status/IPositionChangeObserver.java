package space.status;

import space.model.Vector2D;
import space.objects.AbstractGameObject;

public interface IPositionChangeObserver {
    void positionChanged(AbstractGameObject sender, Vector2D from, Vector2D to);
}
