package space.status;

public class GameData {
    public double money;
    public int engineLevel;
    public int hullLevel;
    public int tankLevel;
    public int noseLevel;
    public int finsLevel;
    public boolean refreshRequired = false;

    public GameData(double money, int engineLevel, int hullLevel,
                    int tankLevel, int noseLevel, int finsLevel){
        this.money = 100000;
        this.engineLevel = engineLevel;
        this.hullLevel = hullLevel;
        this.tankLevel = tankLevel;
        this.noseLevel = noseLevel;
        this.finsLevel = finsLevel;
    }
}
