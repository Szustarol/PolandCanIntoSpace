package space.objects.rocketParts;

public class FuelTank{
    private double fuel;
    private double maxFuel;
    public final int no_tank_levels = 5;

    public double getFuel(){
        return fuel;
    }

    public void used(double deltaTime){
        fuel -= deltaTime;
        if(fuel < 0)
            fuel = 0;
    }

    public void addFuel(double fuel){
        fuel += fuel;
        if(fuel > maxFuel){
            fuel = maxFuel;
        }
        if(fuel < 0)
            fuel = 0;
    }

    public static double [] upgrade_costs = {
        100, 500, 1500, 20000, -1
    };

    public FuelTank(int tankLevel){
        maxFuel = new double[] {20, 50, 100, 150, 200, 250}[tankLevel];
        fuel = maxFuel;
    }
}