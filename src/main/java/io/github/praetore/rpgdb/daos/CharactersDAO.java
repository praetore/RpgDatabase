package io.github.praetore.rpgdb.daos;

import io.github.praetore.rpgdb.models.CharactersEntity;

import javax.persistence.EntityTransaction;

/**
 * Created by darryl on 6-10-15.
 */
public class CharactersDAO extends DataAccessObject {
    public CharactersEntity findCharacter(String characterName) {
        return getEntityManager().find(CharactersEntity.class, characterName);
    }

    public void createNewCharacter(CharactersEntity character) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();
            getEntityManager().persist(character);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
