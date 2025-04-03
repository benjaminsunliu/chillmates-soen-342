package app.mappers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DataMapper<T> {
    protected static EntityManager entityManager;

    public DataMapper() {
        entityManager = JPAUtil.getEntityManager();
    }

    public void create(T entity){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch(Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void update(T entity){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T t = entityManager.merge(entity);
            entityManager.flush();
            entityManager.refresh(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(T entity){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if(!entityManager.contains(entity)){
                entity = entityManager.merge(entity);
            }
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void close(){
        JPAUtil.close();
    }
}
