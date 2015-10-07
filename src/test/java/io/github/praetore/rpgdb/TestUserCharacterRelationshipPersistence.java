package io.github.praetore.rpgdb;

import io.github.praetore.rpgdb.daos.CharactersDAO;
import io.github.praetore.rpgdb.daos.UsersDAO;
import io.github.praetore.rpgdb.models.CharactersEntity;
import io.github.praetore.rpgdb.models.UsersEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by darryl on 7-10-15.
 */
public class TestUserCharacterRelationshipPersistence {
    private static CharactersDAO charactersDAO;
    private static UsersDAO usersDAO;
    private static final String PERSISTENCE_UNIT_NAME = "rpgUnit";
    private static final String NAME = "Ulamog";
    private static final String RACE = "Warhawk";
    private static final String CLASS = "Knight";
    private static final int LEVEL = 1;

    private static final String USERNAME = "Praetore";
    private static final int BALANCE = 100;
    private static final String FIRSTNAME = "Darryl";
    private static final String LASTNAME = "Amatsetam";
    private static final String IBAN = "123456789";
    private static final short CHARACTERSLOTS = (short) 1;
    private static final short MONTHSPAYED = (short) 0;
    private static final String PASSWORD = "verysecret";
    private static final boolean ISBANNED = false;

    @BeforeClass
    public static void initFixture() {
        charactersDAO = new CharactersDAO();
        charactersDAO.setEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        usersDAO = new UsersDAO();
        usersDAO.setEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Test
    public void testRelationship() {
        CharactersEntity charactersEntity = new CharactersEntity();
        charactersEntity.setName(NAME);
        charactersEntity.setClazz(CLASS);
        charactersEntity.setLevel(LEVEL);
        charactersEntity.setRace(RACE);

        charactersDAO.createNewCharacter(charactersEntity);
        CharactersEntity character = charactersDAO.findCharacter(NAME);

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserName(USERNAME);
        usersEntity.setBalance(BALANCE);
        usersEntity.setFirstName(FIRSTNAME);
        usersEntity.setLastName(LASTNAME);
        usersEntity.setIban(IBAN);
        usersEntity.setCharacterSlots(CHARACTERSLOTS);
        usersEntity.setMonthsPayed(MONTHSPAYED);
        usersEntity.setPassword(PASSWORD);
        usersEntity.setBanned(ISBANNED);

        usersDAO.createNewUser(usersEntity);
        UsersEntity user = usersDAO.findUser(USERNAME);
        usersDAO.addCharacterToUser(user, character);
        int amount = usersDAO.getCurrentCharacterAmount(USERNAME);
        assertEquals(amount, 1);
    }

    @AfterClass
    public static void destroyFixture() {
        charactersDAO.close();
        usersDAO.close();
    }
}
