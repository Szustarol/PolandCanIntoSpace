package space.objects.staticObjects;

import space.model.Vector2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cloud extends StaticGameObject{

    static BufferedImage image = null;

    @Override
    public BufferedImage getImage(float rotation) {
        return image;
    }

    public Cloud(Vector2D position) {
        super(position);
        if(image == null){
            try{
                image = ImageIO.read(new File("Data/Static/cloud.png"));
            }catch (IOException e){
                System.out.println("Error while reading image file: " + "Data/Static/cloud.png");
            }
        }
    }


}
