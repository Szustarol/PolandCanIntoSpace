package space.objects.rocketParts;

public class Nose extends  AbstractRocketPart{
    public  static final int no_nose_levels = 5;
    private static final String [] data_path = {
            null,
            "Data/Nose/nose_level_1.png",
            "Data/Nose/nose_level_2.png",
            "Data/Nose/nose_level_3.png",
            "Data/Nose/nose_level_4.png"
    };
    private static final double [] hitpoints = {
            0,
            20,
            40,
            50,
            70
    };
    private static final double [] weights = {
            0,
            70,
            100,
            120,
            80
    };
    public static final double [] upgrade_costs = {
            1000,
            7000,
            25000,
            50000,
            -1
    };

    public static final double [] vertical_drag_coefficients = {
            1,
            0.9,
            0.8,
            0.6,
            0.4,
            0.2
    };

    public final double verticalDragCoefficient;

    public Nose(int noseLevel){
        loadPath = data_path[noseLevel];
        weight = weights[noseLevel];
        health = hitpoints[noseLevel];
        nextPrice = upgrade_costs[noseLevel];
        image = null;
        verticalDragCoefficient = vertical_drag_coefficients[noseLevel];
    }
}
