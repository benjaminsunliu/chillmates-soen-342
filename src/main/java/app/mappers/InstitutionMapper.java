package app.mappers;

import app.auction.Institution;
import app.auction.ObjectOfInterest;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class InstitutionMapper extends DataMapper {
    public List<List<ObjectOfInterest>> findAllObjectsByInstitution() {
        TypedQuery<Institution> query = entityManager.createQuery(
                "SELECT i FROM Institution i", Institution.class);
        List<Institution> institutions = query.getResultList();

        List<List<ObjectOfInterest>> objectsByInstitution = new ArrayList<>();
        for (Institution institution : institutions) {
            objectsByInstitution.add(institution.getObjects());
        }

        return objectsByInstitution;
    }

    public List<Institution> findAll() {
        TypedQuery<Institution> query = entityManager.createQuery(
                "SELECT i FROM Institution i", Institution.class);
        return query.getResultList();
    }

    public Institution findByName(String institutionName) {
        TypedQuery<Institution> query = entityManager.createQuery(
                "SELECT i FROM Institution i WHERE i.name = :name", Institution.class);
        query.setParameter("name", institutionName);
        List<Institution> institutions = query.getResultList();

        if (institutions.isEmpty()) {
            return null;
        } else {
            return institutions.get(0);
        }
    }
}
