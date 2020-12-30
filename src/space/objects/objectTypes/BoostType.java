package space.objects.objectTypes;

import space.model.Vector2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public enum BoostType {
    SMALL,
    MEDIUM,
    LARGE;

    private static BufferedImage[] image = null;

    public Vector2D value(){
        return switch (this) {
            case SMALL -> new Vector2D(0, 100);
            case MEDIUM -> new Vector2D(0, 200);
            case LARGE -> new Vector2D(0, 500);
        };
    }

    public BufferedImage getImage(){
        if(image == null){
            image = new BufferedImage[values().length];
        }

        if(image[this.ordinal()] == null){
            String filename = switch (this){
                case SMALL -> "Data/Boost/boost_level_1.png";
                case MEDIUM -> "Data/Boost/boost_level_2.png";
                case LARGE -> "Data/Boost/boost_level_3.png";
            };
            try{
                image[this.ordinal()] = ImageIO.read(new File(filename));
            }catch (IOException e){
                System.out.println("error while trying to load file from " + filename + ": " + e);
            }
        }


        return image[this.ordinal()];
    }
}
