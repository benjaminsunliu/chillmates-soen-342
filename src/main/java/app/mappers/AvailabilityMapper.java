package app.mappers;

import app.services.Availability;

import java.util.List;

public class AvailabilityMapper extends DataMapper{
    public List<Availability> findAll() {
        return entityManager.createQuery("SELECT a FROM Availability a", Availability.class).getResultList();
    }

}
