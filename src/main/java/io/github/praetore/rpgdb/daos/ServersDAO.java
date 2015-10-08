package io.github.praetore.rpgdb.daos;

import io.github.praetore.rpgdb.models.ServersEntity;

import javax.persistence.EntityTransaction;

/**
 * Created by darryl on 7-10-15.
 */
public class ServersDAO extends DataAccessObject {
    public ServersEntity findServer(String serverAddress) {
        return getEntityManager().find(ServersEntity.class, serverAddress);
    }

    public void createNewServer(ServersEntity serversEntity) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();
            getEntityManager().persist(serversEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
