package io.github.praetore.rpgdb.beans;

import io.github.praetore.rpgdb.models.UsersEntity;

import javax.persistence.EntityTransaction;

/**
 * Created by darryl on 6-10-15.
 */
public class UserServiceBean extends ServiceBean {
    public UsersEntity findUser(String userName) {
        return getEntityManager().find(UsersEntity.class, userName);
    }

    public void createUser(UsersEntity usersEntity) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        transaction.begin();
        getEntityManager().persist(usersEntity);
        transaction.commit();
    }
}
