package app.users;
import java.util.ArrayList;
import java.util.List;

import app.services.Availability;
import app.services.ServiceRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "Expert")
public class Expert extends User {
    private String licenseNumber;
    private String areaOfExpertise;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "expert", orphanRemoval = true)
    private Availability availability;

    @OneToMany(mappedBy = "assignedExpert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRequest> serviceRequests;

    public Expert(String email, String password, String licenseNumber, String areaOfExpertise) {
        super(email, password);
        this.licenseNumber = licenseNumber;
        this.areaOfExpertise = areaOfExpertise;
        this.availability = new Availability();
    }

    public Expert() {
        super();
        this.availability = new Availability();
    }

    public String getLicenseNumber() {
        return this.licenseNumber;
    }
    public String getAreaOfExpertise() {
        return this.areaOfExpertise;
    }
    public Availability getAvailability() {
        return this.availability;
    }
    public List<ServiceRequest> getServiceRequests() {
        return this.serviceRequests;
    }

    @Override
    public String getRole() {
        return "Expert";
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
    public void setServiceRequests(List<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public Object getId() {
        return this.id;
    }
}
