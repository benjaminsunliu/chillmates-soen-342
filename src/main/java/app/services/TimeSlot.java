package app.services;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TimeSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "availability_id")
    private Availability availability;

    @OneToOne(mappedBy = "timeSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime, ServiceRequest serviceRequest) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.serviceRequest = serviceRequest;
    }

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime, Availability availability) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.availability = availability;
    }

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime, Availability availability, ServiceRequest serviceRequest) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.availability = availability;
        this.serviceRequest = serviceRequest;
    }

    public TimeSlot() {

    }


    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequests(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public boolean overlaps(TimeSlot other) {
        return !this.endTime.isBefore(other.startTime) && !this.startTime.isAfter(other.endTime);
    }

    public Availability getAvailability() {
        return this.availability;
    }
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Start time: " + this.startTime + " - End time: " + this.endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
