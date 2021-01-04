package space.objects.objectTypes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum CoinType {
    BRONZE,
    SILVER,
    GOLD,
    BLACK,
    WHITE;

    private static BufferedImage[] image = null;

    public double value(){
        return switch (this){
            case BRONZE -> 10;
            case SILVER -> 50;
            case GOLD -> 250;
            case BLACK -> 500;
            case WHITE -> 1000;
        };
    }

    public BufferedImage getImage(){
        if(image == null){
            image = new BufferedImage[values().length];
        }

        if(image[this.ordinal()] == null){
            String filename = switch (this){
                case BRONZE -> "Data/Coins/coin_bronze.png";
                case SILVER -> "Data/Coins/coin_silver.png";
                case GOLD -> "Data/Coins/coin_gold.png";
                case BLACK -> "Data/Coins/coin_black.png";
                case WHITE -> "Data/Coins/coin_white.png";
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
