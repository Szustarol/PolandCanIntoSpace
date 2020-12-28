package space.status;

import space.model.Vector2D;
import space.objects.AbstractGameObject;

import java.util.LinkedList;
import java.util.TreeMap;

public class Map implements IPositionChangeObserver{
    private TreeMap<Vector2D, LinkedList<AbstractGameObject>> objects;

    private double mapWidth = 2000;

    public Map(double mapWidth){
        objects = new TreeMap<>();
        this.mapWidth = mapWidth;
    }


    public Vector2D targetPositionMapping(Vector2D position){
        while(position.x > mapWidth/2){
            position = position.add(new Vector2D(-mapWidth, 0));
        }

        while(position.x < mapWidth/2){
            position = position.add(new Vector2D(mapWidth, 0));
        }
        return  position;
    }


    public LinkedList<AbstractGameObject> getObjectsBetween(double fromy, double toy){
        LinkedList<AbstractGameObject> list = new LinkedList<>();
        objects.tailMap(new Vector2D(-1000, fromy)).headMap(new Vector2D(1000, toy));
        objects.values().forEach(lst -> {
            list.addAll(lst);
        });
        return list;
    }

    public void addObject(AbstractGameObject object){
        Vector2D pos = object.getPosition();
        if (!objects.containsKey(pos)) {
            objects.put(pos, new LinkedList<AbstractGameObject>());
        }
        objects.get(pos).add(object);
    }

    public void removeObject(AbstractGameObject object){
        Vector2D pos = object.getPosition();
        if(objects.containsKey(pos)){
            objects.get(pos).remove(object);
        }
        if(objects.get(pos).size() == 0){
            objects.remove(pos);
        }
    }

    private void removeObjectFromPosition(AbstractGameObject object, Vector2D position){
        if(objects.containsKey(position)){
            objects.get(position).remove(object);
        }
        if(objects.get(position).size() == 0){
            objects.remove(position);
        }
    }

    @Override
    public void positionChanged(AbstractGameObject sender, Vector2D from, Vector2D to) {
        removeObjectFromPosition(sender, from);
        addObject(sender);
    }
}
