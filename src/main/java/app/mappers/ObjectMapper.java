package app.mappers;

import app.auction.ObjectOfInterest;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ObjectMapper extends DataMapper{
    public List<ObjectOfInterest> findAll() {
        TypedQuery<ObjectOfInterest> query = entityManager.createQuery(
                "SELECT o FROM ObjectOfInterest o", ObjectOfInterest.class);
        return query.getResultList();
    }
}
