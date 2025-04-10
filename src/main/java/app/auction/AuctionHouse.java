package app.auction;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class AuctionHouse {
    private String name;
    private String location;
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "auctionHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auction> auctions;

    public AuctionHouse(String name, String location) {
        this.auctions = new java.util.ArrayList<>();
        this.name = name;
        this.location = location;
    }

    public AuctionHouse() {

    }

    public String getName() {
        return this.name;
    }
    public String getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nLocation: " + this.location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }
    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
    public void addAuction(Auction auction) {
        this.auctions.add(auction);
        auction.setAuctionHouse(this); // Assuming Auction has a setAuctionHouse method
    }
    public void removeAuction(Auction auction) {
        this.auctions.remove(auction);
        auction.setAuctionHouse(null); // Assuming Auction has a setAuctionHouse method
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }

}
