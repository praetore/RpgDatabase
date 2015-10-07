package io.github.praetore.rpgdb.beans;

import javax.persistence.*;

/**
 * Created by darryl on 7-10-15.
 */
abstract public class ServiceBean {
    @PersistenceUnit(unitName = "rpgUnit")
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext(unitName = "rpgUnit")
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(String persistenceUnit) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        this.entityManager = getEntityManagerFactory().createEntityManager();
    }

    public void close() {
        this.entityManager.close();
        this.entityManagerFactory.close();
    }
}
