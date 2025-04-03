package app.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Administrator")
public class Administrator extends User {
    public Administrator(String email, String password) {
        super(email, password);
    }

    public Administrator() {
        super();
    }

    @Override
    public String getRole() {
        return "Administrator";
    }

}
