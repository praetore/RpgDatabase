package io.github.praetore.rpgdb.beans;

import io.github.praetore.rpgdb.models.CharactersEntity;

import javax.persistence.EntityTransaction;

/**
 * Created by darryl on 6-10-15.
 */
public class CharacterServiceBean extends ServiceBean {
    public CharactersEntity findCharacter(String characterName) {
        return getEntityManager().find(CharactersEntity.class, characterName);
    }

    public void createCharacter(CharactersEntity character) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        transaction.begin();
        getEntityManager().persist(character);
        transaction.commit();
    }

}
