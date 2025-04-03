package app.mappers;

import app.users.Client;
import app.users.Expert;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Type;
import java.util.List;

import app.catalogs.UserCatalog;
import app.users.User;

public class UserMapper extends DataMapper<User>{
    private UserCatalog userCatalog;

    public UserMapper() {
        super();
        userCatalog = UserCatalog.getInstance();
    }

    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByCredentials(String email, String password) {
        try{
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public List<User> findNonAdministrators() {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE TYPE(u) <> Administrator", User.class);
        return query.getResultList();
    }

    public List<Client> findNonApprovedClients() {
        TypedQuery<Client> query = entityManager.createQuery(
                "SELECT c FROM Client c WHERE c.status = false", Client.class);
        return query.getResultList();
    }

    public List<Client> findApprovedClients() {
        TypedQuery<Client> query = entityManager.createQuery(
                "SELECT c FROM Client c WHERE c.status = true", Client.class);
        return query.getResultList();
    }

    public List<Client> findClients() {
        TypedQuery<Client> query = entityManager.createQuery(
                "SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }

    public List<Expert> findExperts() {
        TypedQuery<Expert> query = entityManager.createQuery(
                "SELECT e FROM Expert e", Expert.class);
        return query.getResultList();
    }

}
