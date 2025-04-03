package app.mappers;

import app.services.ServiceRequest;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ServiceRequestMapper extends DataMapper{
    public List<ServiceRequest> findAll() {
        try{
            TypedQuery<ServiceRequest> query = entityManager.createQuery(
                    "SELECT sr FROM ServiceRequest sr", ServiceRequest.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
