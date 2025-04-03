package app.catalogs;
import java.util.ArrayList;
import java.util.List;

import app.Institution;
import app.auction.ObjectOfInterest;

public class ObjectCatalog {
    private List<ObjectOfInterest> objects;

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

    public Object addObject(String name, String description, String type, Institution institution) {
        ObjectOfInterest object = new ObjectOfInterest(name, description, type, institution);
        this.addObject(object);
        return object;
    }
}
