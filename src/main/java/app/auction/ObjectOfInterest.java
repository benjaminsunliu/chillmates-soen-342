package app.auction;

import app.Institution;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ObjectOfInterest {
    private String title;
    private String description;
    private String type;
    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;
    @Id
    @GeneratedValue
    private Long id;

    public ObjectOfInterest(String title, String description, String type, Institution institution) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.institution = institution;
    }

    public ObjectOfInterest() {

    }

    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }
    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "\nDescription: " + this.description + "\nType: " + this.type + "\nInstitution: " + this.institution.getName() + "\n";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
    public Institution getInstitution() {
        return institution;
    }

}
