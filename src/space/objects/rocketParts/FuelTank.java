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

    public FuelTank(int tankLevel){
        maxFuel = new double[] {100, 200, 500, 1000, 3000}[tankLevel];
        fuel = maxFuel;
    }
}