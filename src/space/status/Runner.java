package space.status;

import space.objects.AbstractGameObject;
import space.objects.Rocket;

import java.util.LinkedList;
import java.util.TreeSet;

public class Runner {

    private Map map;
    private TreeSet<AbstractGameObject> gameObjectSet;

    public Runner(){
        map = new Map();
        gameObjectSet = new TreeSet<>();
        Rocket rocket= new Rocket();
        gameObjectSet.add(rocket);
        map.addObject(rocket);
    }

    public LinkedList<AbstractGameObject> getDrawableBetween(double ystart, double yend){
        return map.getObjectsBetween(ystart, yend);
    }


}
