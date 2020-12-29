package space.objects;

import space.model.BoundingBox;
import space.model.Vector2D;
import space.objects.objectTypes.CoinType;

import java.awt.image.BufferedImage;

public class Coin extends AbstractGameObject{

    private double value;
    private CoinType type;

    public Coin(CoinType type, Vector2D coinPosition){
        super(coinPosition, false);
        gameObjectType = GameObjectType.COIN;
        value = type.value();
        this.type = type;
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

    public BufferedImage getImage(float rotation){
        //ignores rotation
        return type.getImage();
    }
}
