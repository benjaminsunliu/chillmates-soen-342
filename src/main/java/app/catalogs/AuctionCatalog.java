package app.catalogs;
import java.util.ArrayList;
import java.util.List;

import app.auction.Auction;

public class AuctionCatalog {
    private List<Auction> auctions;
    private static AuctionCatalog instance;

    public AuctionCatalog() {
        this.auctions = new ArrayList<>();
    }

    public void addAuction(Auction auction) {
        this.auctions.add(auction);
    }

    public List<Auction> getAuctions() {
        return this.auctions;
    }
    
    public static AuctionCatalog getInstance() {
        if (instance == null) {
            instance = new AuctionCatalog();
        }
        return instance;
    }
}
