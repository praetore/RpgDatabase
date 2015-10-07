package io.github.praetore.rpgdb.daos;

import io.github.praetore.rpgdb.models.CharactersEntity;
import io.github.praetore.rpgdb.models.UsersEntity;

import javax.persistence.EntityTransaction;

/**
 * Created by darryl on 6-10-15.
 */
public class UsersDAO extends DataAccessObject {
    public UsersEntity findUser(String userName) {
        return getEntityManager().find(UsersEntity.class, userName);
    }

    public void createNewUser(UsersEntity usersEntity) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        transaction.begin();
        getEntityManager().persist(usersEntity);
        transaction.commit();
    }

    public void addCharacterToUser(UsersEntity usersEntity, CharactersEntity charactersEntity) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        transaction.begin();
        usersEntity.getCharacters().add(charactersEntity);
        transaction.commit();
    }

    public int getCurrentCharacterAmount(String userName) {
        return findUser(userName).getCharacters().size();
    }
}
