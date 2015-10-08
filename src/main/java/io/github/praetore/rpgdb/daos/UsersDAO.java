package io.github.praetore.rpgdb.daos;

import io.github.praetore.rpgdb.models.CharactersEntity;
import io.github.praetore.rpgdb.models.UsersEntity;

import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by darryl on 6-10-15.
 */
public class UsersDAO extends DataAccessObject {
    private Map<String, Boolean> loggedIn;

    public UsersDAO() {
        this.loggedIn = new HashMap<String, Boolean>();
    }

    public UsersEntity findUser(String userName) {
        return getEntityManager().find(UsersEntity.class, userName);
    }

    public void createNewUser(UsersEntity usersEntity) {
        UsersEntity user = findUser(usersEntity.getUserName());
        if (user == null) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                getEntityManager().persist(usersEntity);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public void loginUser(String userName, String password) {
        UsersEntity user = findUser(userName);
        Boolean authorized = this.loggedIn.get(userName);
        if (user != null && !authorized) {
            if (user.getPassword().equals(password)) {
                this.loggedIn.put(userName, true);
            }
        }
    }

    public CharactersEntity findCharacterFromUser(UsersEntity usersEntity, String characterName) {
        Boolean authorized = this.loggedIn.get(usersEntity.getUserName());
        if (authorized) {
            for (CharactersEntity charactersEntity : usersEntity.getCharacters()) {
                if (charactersEntity.getName().equals(characterName)) {
                    return charactersEntity;
                }
            }
        }
        return null;
    }

    public void addCharacterToUser(UsersEntity usersEntity, CharactersEntity charactersEntity) {
        Boolean authorized = this.loggedIn.get(usersEntity.getUserName());
        if (authorized) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                usersEntity.getCharacters().add(charactersEntity);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public void removeCharacterFromUser(UsersEntity usersEntity, CharactersEntity character) {
        Boolean authorized = this.loggedIn.get(usersEntity.getUserName());
        if (authorized) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                usersEntity.getCharacters().remove(character);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public int getCurrentCharacterAmount(String userName) {
        Boolean authorized = this.loggedIn.get(userName);
        if (authorized) {
            return findUser(userName).getCharacters().size();
        }
        return -1;
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
        Boolean authorized = this.loggedIn.get(userName);
        if (authorized) {
            UsersEntity user = findUser(userName);
            removeUser(user);
        }
    }
}
