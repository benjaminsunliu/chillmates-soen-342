package app.auction;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Viewing {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToOne(mappedBy = "viewing")
    private Auction auction;
    @Id
    @GeneratedValue
    private Long id;

    public Viewing(LocalDateTime startTime, LocalDateTime endTime, Auction scheduledAuction) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.auction = scheduledAuction;
    }

    public Viewing() {

    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }
    public LocalDateTime getEndTime() {
        return this.endTime;
    }
    public Auction getAuction() {
        return this.auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Start time: " + this.startTime + "\nEnd time: " + this.endTime + "\nAuction: " + this.auction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
