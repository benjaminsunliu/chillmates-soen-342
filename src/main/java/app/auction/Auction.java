package app.auction;
import app.mappers.AuctionMapper;
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


    public Auction(String auctionType, AuctionHouse auctionHouse, boolean isOnline, Viewing viewing) {
        this.auctionHouse = auctionHouse;
        this.auctionType = auctionType;
        this.isOnline = isOnline;
        this.viewing = viewing;
    }
    public Auction(String auctionType, AuctionHouse auctionHouse, boolean isOnline) {
        this.auctionHouse = auctionHouse;
        this.auctionType = auctionType;
        this.isOnline = isOnline;
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


}
