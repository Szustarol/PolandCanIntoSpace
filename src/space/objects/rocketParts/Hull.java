package space.objects.rocketParts;

import java.awt.image.BufferedImage;

public class Hull extends AbstractRocketPart{

    public  static final int no_hull_levels = 4;
    private static final String [] hull_data_path = {
            "Data/Hull/hull_level_1.png",
            "Data/Hull/hull_level_2.png",
            "Data/Hull/hull_level_3.png",
            "Data/Hull/hull_level_4.png"
    };
    private static final double [] hull_hitpoints = {
            100,
            150,
            300,
            500
    };
    private static final double [] hull_weights = {
            1000,
            800,
            700,
            550
    };
    public static final double [] upgrade_costs = {
            2000,
            5000,
            15000,
            -1
    };

    private BufferedImage image;

    public Hull(int hullLevel){
        loadPath = hull_data_path[hullLevel];
        weight = hull_weights[hullLevel];
        health = hull_hitpoints[hullLevel];
        nextPrice = upgrade_costs[hullLevel];
    }


}
