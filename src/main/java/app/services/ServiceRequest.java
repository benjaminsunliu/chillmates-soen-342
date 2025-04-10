package app.services;

import app.users.*;
import jakarta.persistence.*;

@Entity
public class ServiceRequest {

    private String requestType;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Expert assignedExpert;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client requestingClient;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    @Id
    @GeneratedValue
    private Long id;

    public ServiceRequest(String requestType, Expert assignedExpert, Client requestingClient, TimeSlot timeSlot) {
        this.requestType = requestType;
        this.assignedExpert = assignedExpert;
        this.requestingClient = requestingClient;
        this.timeSlot = timeSlot;
    }

    public ServiceRequest() {

    }

    public String getRequestType() {
        return this.requestType;
    }
    public Expert getAssignedExpert() {
        return this.assignedExpert;
    }
    public Client getRequestingClient() {
        return this.requestingClient;
    }
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    public void setAssignedExpert(Expert expert) {
        this.assignedExpert = expert;
    }
    public void setRequestingClient(Client client) {
        this.requestingClient = client;
    }
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String toString() {
        return "Service Request ID: " + this.id + "\n\t- Request Type: " + this.requestType + "\n\t- Assigned Expert: "  + this.assignedExpert.getEmail() + "\n\t- Requesting Client: " + this.requestingClient.getEmail() + "\n\t- Time Slot: " + this.timeSlot.toString() + "\n";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
