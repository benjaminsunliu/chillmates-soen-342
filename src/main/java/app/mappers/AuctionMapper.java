package app.mappers;

import app.auction.Auction;
import app.auction.Viewing;
import app.catalogs.AuctionCatalog;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AuctionMapper extends DataMapper {

    private static AuctionCatalog auctioncatalog;

    public AuctionMapper(){
        super();
        auctioncatalog = AuctionCatalog.getInstance();
    }

    public List<Auction> findAll() {
        TypedQuery<Auction> query = entityManager.createQuery(
                "SELECT a FROM Auction a order by a.id", Auction.class);
        auctioncatalog.setAuctions(query.getResultList());
        return auctioncatalog.getAuctions();
    }

}
