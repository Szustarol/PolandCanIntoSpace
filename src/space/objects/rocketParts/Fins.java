package space.objects.rocketParts;

public class Fins extends AbstractRocketPart{

    public  static final int no_fins_levels = 6;
    private static final String [] data_path = {
            null,
            "Data/Fins/fins_level_1.png",
            "Data/Fins/fins_level_2.png",
            "Data/Fins/fins_level_3.png",
            "Data/Fins/fins_level_4.png",
            "Data/Fins/fins_level_5.png"
    };
    private static final double [] hitpoints = {
            0,
            40,
            70,
            100,
            120,
            130
    };
    private static final double [] weights = {
            150,
            100,
            120,
            100,
            80,
            80
    };
    public static final double [] upgrade_costs = {
            100,
            500,
            1000,
            2000,
            4000,
            -1
    };

    public static final double [] horizontal_coefficients = {
            0.01,
            0.05,
            0.06,
            0.07,
            0.1,
            0.12
    };

    public static final double [] vertical_drag_coefficients = {
            2,
            1,
            0.5,
            0.3,
            0.2,
            0.2
    };

    public final double verticalDragCoefficient;
    public final double horizontalCoefficient;

    public Fins(int finsLevel){
        loadPath = data_path[finsLevel];
        weight = weights[finsLevel];
        health = hitpoints[finsLevel];
        nextPrice = upgrade_costs[finsLevel];
        image = null;
        verticalDragCoefficient = vertical_drag_coefficients[finsLevel];
        horizontalCoefficient = horizontal_coefficients[finsLevel];
    }
}
