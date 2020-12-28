package space.objects.objectTypes;

public enum CoinType {
    BRONZE,
    SILVER,
    GOLD,
    BLACK,
    WHITE;

    public double value(){
        return switch (this){
            case BRONZE -> 10;
            case SILVER -> 50;
            case GOLD -> 250;
            case BLACK -> 1000;
            case WHITE -> 2500;
        };
    }
}
