package app.catalogs;
import java.util.ArrayList;
import java.util.List;

import app.auction.Institution;
import app.auction.ObjectOfInterest;

public class ObjectCatalog {
    private List<ObjectOfInterest> objects;
    private static ObjectCatalog instance;

    public ObjectCatalog() {
        this.objects = new ArrayList<>();
    }

    public void addObject(ObjectOfInterest object) {
        this.objects.add(object);
    }

    public List<ObjectOfInterest> getObjects() {
        return this.objects;
    }

    public Object[] getAvailableObjects(){
        return this.objects.toArray();
    }

    public static ObjectCatalog getInstance() {
        if (instance == null) {
            instance = new ObjectCatalog();
        }
        return instance;
    }

    public void setObjects(List<ObjectOfInterest> objects){
        this.objects = objects;
    }

    public Object addObject(String name, String description, String type, Institution institution) {
        ObjectOfInterest object = new ObjectOfInterest(name, description, type, institution);
        this.addObject(object);
        return object;
    }
}
