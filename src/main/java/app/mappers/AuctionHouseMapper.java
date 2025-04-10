package app.mappers;

import app.auction.Auction;
import app.auction.AuctionHouse;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class AuctionHouseMapper extends DataMapper{
    public List<List<Auction>> findAllAuctionsByAuctionHouse() {
        TypedQuery<AuctionHouse> query = entityManager.createQuery(
                "SELECT ah FROM AuctionHouse ah", AuctionHouse.class);
        List<AuctionHouse> houses = query.getResultList();

        List<List<Auction>> auctionsByHouse = new ArrayList<>();
        for (AuctionHouse house : houses) {
            auctionsByHouse.add(house.getAuctions()); // assumes getAuctions() is defined
        }

        return auctionsByHouse;
    }

    public List<AuctionHouse> findAll() {
        TypedQuery<AuctionHouse> query = entityManager.createQuery(
                "SELECT ah FROM AuctionHouse ah", AuctionHouse.class);
        return query.getResultList();
    }
}
