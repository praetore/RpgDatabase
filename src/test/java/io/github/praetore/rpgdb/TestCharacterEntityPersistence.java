package io.github.praetore.rpgdb;

import io.github.praetore.rpgdb.daos.CharactersDAO;
import io.github.praetore.rpgdb.models.CharactersEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by darryl on 7-10-15.
 */
public class TestCharacterEntityPersistence {
    private static CharactersDAO dao;
    private static final String PERSISTENCE_UNIT_NAME = "rpgUnit";
    private static final String NAME = "Ulamog";
    private static final String RACE = "Warhawk";
    private static final String CLASS = "Knight";
    private static final int LEVEL = 1;

    @BeforeClass
    public static void initFixture() throws Exception {
        dao = new CharactersDAO();
        dao.setEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Test
    public void testUserEntityPersistence() throws Exception {
        CharactersEntity entity = new CharactersEntity();
        entity.setName(NAME);
        entity.setClazz(CLASS);
        entity.setLevel(LEVEL);
        entity.setRace(RACE);

        dao.createNewCharacter(entity);
        CharactersEntity character = dao.findCharacter(NAME);
        String name = character.getName();
        assertEquals(name, NAME);
        String clazz = character.getClazz();
        assertEquals(clazz, CLASS);
        int level = character.getLevel();
        assertEquals(level, LEVEL);
        String race = character.getRace();
        assertEquals(race, RACE);
    }

    @AfterClass
    public static void destroyFixture() throws Exception {
        dao.close();
    }
}
