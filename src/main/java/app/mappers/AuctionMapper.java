package app.mappers;

import app.auction.Auction;
import app.auction.Viewing;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AuctionMapper extends DataMapper {
    public List<Auction> findAll() {
        TypedQuery<Auction> query = entityManager.createQuery(
                "SELECT a FROM Auction a", Auction.class);
        return query.getResultList();
    }
}
