package app.mappers;

import app.auction.Auction;
import app.auction.AuctionHouse;
import app.auction.Viewing;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AuctionMapper extends DataMapper {
    public List<Auction> findAll() {
        TypedQuery<Auction> query = entityManager.createQuery(
                "SELECT a FROM Auction a", Auction.class);
        return query.getResultList();
    }


    public List<Viewing> findAllViewings() {
        TypedQuery<Viewing> query = entityManager.createQuery(
                "SELECT v FROM Viewing v", Viewing.class);
        return query.getResultList();
    }

    public void updateViewing(Viewing viewing) {
        entityManager.getTransaction().begin();
        entityManager.merge(viewing);
        entityManager.getTransaction().commit();
    }

    public void createViewing(Viewing viewing) {
        entityManager.getTransaction().begin();
        entityManager.persist(viewing);
        entityManager.getTransaction().commit();
    }

    public void deleteViewing(Viewing selectedViewing) {
        entityManager.getTransaction().begin();
        Viewing managedViewing = entityManager.find(Viewing.class, selectedViewing.getId());
        if (managedViewing != null) {
            entityManager.remove(managedViewing);
        }
        entityManager.getTransaction().commit();
    }
}
