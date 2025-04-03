package app.mappers;

import java.util.logging.Logger;
import java.util.logging.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "soen342";
    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            Logger hibernateLogger = Logger.getLogger("org.hibernate");
            hibernateLogger.setLevel(Level.SEVERE);
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Initial EntityManagerFactory creation failed.");
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
