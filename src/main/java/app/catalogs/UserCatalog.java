package app.catalogs;
import app.users.User;

import java.util.List;
import java.util.ArrayList;

import app.users.*;

public class UserCatalog {
    private List <User> users;
    private static UserCatalog instance;

    public UserCatalog() {
        this.users = new ArrayList<>();
    }
    
    public void addUser(User user) {
        this.users.add(user);
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static UserCatalog getInstance() {
        if (instance == null) {
            instance = new UserCatalog();
        }
        return instance;
    }

    public User findByEmailAndPassword(String email, String password) {
        for (User user : this.users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User findByEmail(String email) {
        for (User user : this.users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean isEmailTaken(String email){
        return this.findByEmail(email) != null;
    }

    public boolean verifyDupe(String email){
        return this.isEmailTaken(email);
    }

    public User createUser(String email, String password, String affiliation, String intent, boolean Status){
        User user = new Client(email, password, affiliation, intent, Status);
        this.addUser(user);
        return user;
    }
}
