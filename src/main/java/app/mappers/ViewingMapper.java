package app.mappers;

import app.auction.Viewing;
import jakarta.persistence.TypedQuery;
import app.catalogs.ViewingCatalog;

import java.util.List;

public class ViewingMapper extends DataMapper{

    private ViewingCatalog viewingcatalog;
    private List<Viewing> viewings;

    public ViewingMapper(){
        super();
        viewingcatalog = ViewingCatalog.getInstance();
    }

    public List<Viewing> findAll(){
        TypedQuery<Viewing> query = entityManager.createQuery(
                "SELECT v FROM Viewing v", Viewing.class);
        viewingcatalog.setViewings(query.getResultList());
        return viewingcatalog.getViewings();
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
