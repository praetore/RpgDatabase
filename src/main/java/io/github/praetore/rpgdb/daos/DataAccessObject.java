package io.github.praetore.rpgdb.daos;

import javax.persistence.*;

/**
 * Created by darryl on 7-10-15.
 */
abstract public class DataAccessObject {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManagerFactory(String persistenceUnit) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public void close() {
        this.entityManager.close();
        this.entityManagerFactory.close();
    }
}
