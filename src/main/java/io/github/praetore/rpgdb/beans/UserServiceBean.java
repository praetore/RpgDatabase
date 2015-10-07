package io.github.praetore.rpgdb.beans;

import io.github.praetore.rpgdb.models.UsersEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 * Created by darryl on 6-10-15.
 */
@Stateless
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
