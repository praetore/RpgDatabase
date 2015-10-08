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
        try {
            transaction.begin();
            getEntityManager().persist(usersEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void loginUser(String userName, String password) {
        UsersEntity user = findUser(userName);

    }

    public CharactersEntity findCharacterFromUser(UsersEntity usersEntity, String characterName) {
        for (CharactersEntity charactersEntity : usersEntity.getCharacters()) {
            if (charactersEntity.getName().equals(characterName)) {
                return charactersEntity;
            }
        }
        return null;
    }

    public void addCharacterToUser(UsersEntity usersEntity, CharactersEntity charactersEntity) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();
            usersEntity.getCharacters().add(charactersEntity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void removeCharacterFromUser(UsersEntity usersEntity, CharactersEntity character) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();
            usersEntity.getCharacters().remove(character);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public int getCurrentCharacterAmount(String userName) {
        return findUser(userName).getCharacters().size();
    }

    public void removeUser(UsersEntity user) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();
            getEntityManager().remove(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void removeUser(String userName) {
        UsersEntity user = findUser(userName);
        removeUser(user);
    }
}
