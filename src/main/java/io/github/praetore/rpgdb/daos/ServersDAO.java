package io.github.praetore.rpgdb.daos;

import io.github.praetore.rpgdb.models.ServersEntity;
import io.github.praetore.rpgdb.models.UsersEntity;

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

    public boolean canConnect(String serverAddress) {
        ServersEntity server = findServer(serverAddress);
        return server.getConnectedUsers() < server.getMaxUsers();
    }

    public void connectToServer(String serverAddress, UsersEntity user) {
        boolean available = canConnect(serverAddress);
        ServersEntity server = findServer(serverAddress);
        if (available) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                server.getUsersEntities().add(user);
                server.setConnectedUsers((short) (server.getConnectedUsers() + 1));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public boolean onServer(String serverAddress, String userName) {
        ServersEntity server = findServer(serverAddress);
        for (UsersEntity usersEntity : server.getUsersEntities()) {
            if (usersEntity.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public void disconnectFromServer(String serverAddress, UsersEntity user) {
        ServersEntity server = findServer(serverAddress);
        boolean isConnected = onServer(serverAddress, user.getUserName());
        if (isConnected) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                server.getUsersEntities().add(user);
                server.setConnectedUsers((short) (server.getConnectedUsers() + 1));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
}
