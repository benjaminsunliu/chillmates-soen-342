package app.mappers;

import app.auction.ObjectOfInterest;
import jakarta.persistence.TypedQuery;
import app.catalogs.ObjectCatalog;

import java.util.List;

public class ObjectMapper extends DataMapper{

    private ObjectCatalog objectcatalog;
    public ObjectMapper(){
        super();
        objectcatalog = ObjectCatalog.getInstance();
    }

    public List<ObjectOfInterest> findAll() {
        TypedQuery<ObjectOfInterest> query = entityManager.createQuery(
                "SELECT o FROM ObjectOfInterest o", ObjectOfInterest.class);
        objectcatalog.setObjects(query.getResultList());
        return objectcatalog.getObjects();
    }
    public List<ObjectOfInterest> findWithoutAuction() {
        TypedQuery<ObjectOfInterest> query = entityManager.createQuery(
                "SELECT o FROM ObjectOfInterest o WHERE o.auction IS NULL", ObjectOfInterest.class);
        return query.getResultList();
    }
}
