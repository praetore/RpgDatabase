package io.github.praetore.rpgdb.daos;

import javax.persistence.*;
import java.util.Properties;

/**
 * Created by darryl on 7-10-15.
 */
abstract public class DataAccessObject {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManagerFactory(String persistenceUnit, Properties properties) {
        if (properties == null) {
            this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        } else {
            this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, properties);
        }
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void setEntityManagerFactory(String persistenceUnit) {
        setEntityManagerFactory(persistenceUnit, null);
    }

    public void close() {
        this.entityManager.close();
        this.entityManagerFactory.close();
    }
}
