package app.catalogs;
import java.util.ArrayList;
import java.util.List;

import app.auction.AuctionHouse;

public class AuctionHouseCatalog {
    private List<AuctionHouse> auctionHouses;

    public AuctionHouseCatalog() {
        this.auctionHouses = new ArrayList<>();
    }

    public void addAuctionHouse(AuctionHouse auctionHouse) {
        this.auctionHouses.add(auctionHouse);
    }

    public List<AuctionHouse> getAuctionHouses() {
        return this.auctionHouses;
    }
}