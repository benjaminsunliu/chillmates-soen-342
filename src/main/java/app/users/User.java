package app.users;

import jakarta.persistence.*;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public abstract class User {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected long id;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected String password;

    // Constructors
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    // Getters
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }

    public abstract String getRole();

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
