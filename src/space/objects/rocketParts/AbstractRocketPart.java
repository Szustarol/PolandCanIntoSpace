package space.objects.rocketParts;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class AbstractRocketPart {

    protected double weight;
    protected double nextPrice;
    protected double health;

    protected String loadPath = null;

    protected BufferedImage image = null;

    public double partWeight(){
        return weight;
    }

    public double upgradeCost(){
        return nextPrice;
    }

    public double partHealth(){
        return health;
    }

    public BufferedImage getImage(){
        if(image == null){
            try{
                if(loadPath == null){
                    image = null;
                }
                else{
                    image = ImageIO.read(new File(loadPath));
                }
            }catch(IOException e){
                System.out.println("Error while loading image from path:" + loadPath + ": " + e);
            }
        }

        return image;
    }
}
