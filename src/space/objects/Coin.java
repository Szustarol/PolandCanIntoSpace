package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.objectTypes.CoinType;

public class Coin extends AbstractGameObject{

    private double value;

    public Coin(CoinType type){
        super(new Vector2D(0, 0), false);
        gameObjectType = GameObjectType.COIN;
        value = type.value();
    }

    @Override
    public BoundingBox getHitBox() {
        return null;
    }

    @Override
    public void interact(AbstractGameObject another) {
        if(another.gameObjectType == GameObjectType.ROCKET){
            //TODO: add coin to the wallet
        }
    }
}
