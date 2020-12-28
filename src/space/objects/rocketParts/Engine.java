package space.objects.rocketParts;

public class Engine extends AbstractRocketPart{
    public  static final int no_engine_levels = 4;
    private static final String [] engine_data_path = {
            "Data/Engine/engine_level_1.png",
            "Data/Engine/engine_level_2.png",
            "Data/Engine/engine_level_3.png",
            "Data/Engine/engine_level_4.png"
    };
    private static final double [] engine_hitpoints = {
            10,
            40,
            70,
            100
    };
    private static final double [] engine_forces = {
            10,
            50,
            100,
            400
    };
    private static final double [] engine_weights = {
            100,
            200,
            200,
            150
    };
    private static final double [] engine_upgrade_costs = {
            100,
            500,
            1000,
            -1
    };

    public Engine(int engineLevel){
        loadPath = engine_data_path[engineLevel];
        weight = engine_weights[engineLevel];
        health = engine_hitpoints[engineLevel];
        nextPrice = engine_upgrade_costs[engineLevel];
        image = null;
    }

}
