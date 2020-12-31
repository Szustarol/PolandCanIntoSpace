package space;

import java.util.TreeMap;
import java.util.Vector;

public class Translations {
    private static TreeMap<String, String> translation;

    private static final String [] names = {
        "rocket_height", "rocket_fuel_left", "upgrade", "hull", "engine", "start_flight", "money"
    };

    private static final String [] tr_pl_PL = {"Wysokość rakiety", "Pozostało paliwa", "Ulepszenie", "kadłub", "silnik",
    "Naciśnij spację, aby rozpocząć nowy lot.", "Pieniądze"};

    public static String getTranslation(String translationName){
        if(!translation.containsKey(translationName)){
            throw new IllegalArgumentException("This translation was not found: "+ translationName);
        }
        return translation.get(translationName);
    }

    public static void init(String lang){
        translation = new TreeMap<>();
        switch (lang){
            case "pl_PL":
                for(int i = 0; i < names.length; i++){
                    translation.put(names[i], tr_pl_PL[i]);
                }
                break;
            default:
                throw new IllegalArgumentException("This translation is not supported");
        }
    }
}
