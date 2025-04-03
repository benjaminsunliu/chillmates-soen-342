package app.mappers;

import app.Institution;
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
}
