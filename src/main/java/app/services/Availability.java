package app.services;

import app.users.Expert;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Availability {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "availability")
    private List<TimeSlot> timeSlots;

    @OneToOne
    @JoinColumn(name = "expert_id")
    private Expert expert;

    public Availability(Expert expert) {
        this.timeSlots = new ArrayList<>();
        this.expert = expert;
    }

    public Availability() {
        this.timeSlots = new ArrayList<>();
    }

    public List<TimeSlot> getTimeSlots() {
        return this.timeSlots;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        this.timeSlots.add(timeSlot);
    }
    public void removeTimeSlot(TimeSlot timeSlot) {
        this.timeSlots.remove(timeSlot);
    }

    @Override
    public String toString() {
        return "\tExpert: " + this.expert.getEmail() + " - Time Slots: " + this.timeSlots;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Expert getExpert() {
        return expert;
    }
    public void setExpert(Expert expert) {
        this.expert = expert;
    }
}
