package app.users;

import app.services.ServiceRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "Client")
public class Client extends User{
    private String affiliation;
    private String intent;
    private boolean status;

    @OneToMany(mappedBy = "requestingClient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRequest> serviceRequests;

    public Client(String email, String password, String affiliation, String intent, Boolean status) {
        super(email, password);
        this.affiliation = affiliation;
        this.intent = intent;
        this.status = status;
    }

    public Client() {
        super();
    }

    public String getAffiliation() {
        return this.affiliation;
    }
    public String getIntent() {
        return this.intent;
    }
    public Boolean getStatus() {
        return this.status;
    }
    @Override
    public String getRole() {
        return "Client";
    }
    public List<ServiceRequest> getServiceRequests() {
        return this.serviceRequests;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
    public void setIntent(String intent) {
        this.intent = intent;
    }
    public void setServiceRequests(List<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public void addServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.add(serviceRequest);
        serviceRequest.setRequestingClient(this);
    }

}
