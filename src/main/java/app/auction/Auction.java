package app.auction;
import app.mappers.AuctionMapper;
import app.services.TimeSlot;
import jakarta.persistence.*;

@Entity
public class Auction {
    private String auctionType;
    private boolean isOnline;
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auction_house_id")
    private AuctionHouse auctionHouse;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "viewing_id", referencedColumnName = "id")
    private Viewing viewing;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "time_slot_id", referencedColumnName = "id")
    private TimeSlot timeSlot;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "object_of_interest_id")
    private ObjectOfInterest objectOfInterest;


    public Auction(String auctionType, AuctionHouse auctionHouse, boolean isOnline, Viewing viewing, TimeSlot timeSlot, ObjectOfInterest objectOfInterest) {
        this.auctionHouse = auctionHouse;
        this.auctionType = auctionType;
        this.isOnline = isOnline;
        this.viewing = viewing;
        this.timeSlot = timeSlot;
        this.objectOfInterest = objectOfInterest;
    }
    public Auction(String auctionType, AuctionHouse auctionHouse, boolean isOnline, TimeSlot timeSlot, ObjectOfInterest objectOfInterest) {
        this.auctionHouse = auctionHouse;
        this.auctionType = auctionType;
        this.isOnline = isOnline;
        this.timeSlot = timeSlot;
        this.objectOfInterest = objectOfInterest;
    }

    public Auction() {

    }


    public String getAuctionType() {
        return this.auctionType;
    }
    public boolean getIsOnline() {
        return this.isOnline;
    }

    @Override
    public String toString() {
        return "Auction type: " + this.auctionType + "\nOnline: " + this.isOnline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public AuctionHouse getAuctionHouse() {
        return auctionHouse;
    }
    public void setAuctionHouse(AuctionHouse auctionHouse) {
        this.auctionHouse = auctionHouse;
    }

    public Viewing getViewing() {
        return viewing;
    }
    public void setViewing(Viewing viewing) {
        this.viewing = viewing;
    }

    public void setAuctionType(String auctionType) {
        this.auctionType = auctionType;
    }
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    public ObjectOfInterest getObjectOfInterest() {
        return objectOfInterest;
    }
    public void setObjectOfInterest(ObjectOfInterest objectOfInterest) {
        this.objectOfInterest = objectOfInterest;
    }


}
