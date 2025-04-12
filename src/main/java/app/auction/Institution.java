package app.auction;
import java.util.ArrayList;
import java.util.List;

import app.users.*;
import jakarta.persistence.*;

@Entity
public class Institution {
    private String name;

    @OneToMany
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "institution_id")
    private List<ObjectOfInterest> ownedObjects;

    @Id
    @GeneratedValue
    private Long id;

    public Institution(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.ownedObjects = new ArrayList<>();
    }

    public Institution() {

    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return this.name;
    }
    public List<User> getUsers() {
        return this.users;
    }
    public List<ObjectOfInterest> getOwnedObjects() {
        return this.ownedObjects;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
    public void addObject(ObjectOfInterest object) {
        this.ownedObjects.add(object);
    }

    public List<ObjectOfInterest> getObjects() {
        return this.ownedObjects;
    }
    public void setObjects(List<ObjectOfInterest> objects) {
        this.ownedObjects = objects;
    }
    public ObjectOfInterest removeObject(ObjectOfInterest object) {
        if (this.ownedObjects.remove(object)) {
            return object;
        }
        return null;
    }

    
}