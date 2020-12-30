package space.status;

import space.model.Vector2D;
import space.objects.AbstractGameObject;
import space.objects.Rocket;

import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class Map implements IPositionChangeObserver{
    private TreeMap<Vector2D, LinkedList<AbstractGameObject>> objects;

    private double mapWidth;

    public Map(double mapWidth){
        objects = new TreeMap<>();
        this.mapWidth = mapWidth;
    }


    public Vector2D targetPositionMapping(Vector2D position){
        Vector2D ret = new Vector2D(position);

        while(ret.x > mapWidth/2){
            ret = ret.add(new Vector2D(-mapWidth, 0));
        }

        while(ret.x < -mapWidth/2){
            ret = ret.add(new Vector2D(mapWidth, 0));
        }
        return ret;
    }


    public LinkedList<AbstractGameObject> getObjectsBetween(double fromy, double toy){
        LinkedList<AbstractGameObject> list = new LinkedList<>();
        SortedMap<Vector2D, LinkedList<AbstractGameObject>> subMap =objects.subMap(new Vector2D(-1500, fromy), new Vector2D(1500, toy));
        subMap.values().forEach(list::addAll);
        return list;
    }

    public void addObject(AbstractGameObject object){
        Vector2D pos = object.getPosition();
        if (!objects.containsKey(pos)) {
            objects.put(pos, new LinkedList<AbstractGameObject>());
        }
        objects.get(pos).add(object);
    }

    private void ensureValidDoubleRemoval(Vector2D position, AbstractGameObject object){
        Vector2D startingKey = position.sub(new Vector2D(1, 1));
        Vector2D endingKey = position.add(new Vector2D(1, 1));
        Vector2D key = objects.higherKey(startingKey);
        while(key.compareTo(endingKey) < 0){
            LinkedList<AbstractGameObject> value = objects.get(key);
            if(value.contains(object)){
                value.remove(object);
                if(value.size() == 0)
                    objects.remove(key);
                break;
            }
            key = objects.higherKey(key);
            if(key == null)
                break;
        }
    }

    public void removeObject(AbstractGameObject object){
        ensureValidDoubleRemoval(object.getPosition(), object);
    }

    private void removeObjectFromPosition(AbstractGameObject object, Vector2D position){
        ensureValidDoubleRemoval(position, object);
    }

    @Override
    public void positionChanged(AbstractGameObject sender, Vector2D from, Vector2D to) {
        removeObjectFromPosition(sender, from);
        addObject(sender);
    }
}
