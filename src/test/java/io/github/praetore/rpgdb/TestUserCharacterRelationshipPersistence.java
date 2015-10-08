package io.github.praetore.rpgdb;

import io.github.praetore.rpgdb.daos.UsersDAO;
import io.github.praetore.rpgdb.models.CharactersEntity;
import io.github.praetore.rpgdb.models.UsersEntity;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by darryl on 7-10-15.
 */
public class TestUserCharacterRelationshipPersistence {
    private static UsersDAO usersDAO;
    private static final String PERSISTENCE_UNIT_NAME = "rpgUnit";

    private static final String NAME = "Ulamog";
    private static final String RACE = "Warhawk";
    private static final String CLASS = "Knight";
    private static final int LEVEL = 1;

    private static final String NAME_2 = "Dedelore";
    private static final String RACE_2 = "Troll";
    private static final String CLASS_2 = "Rogue";
    private static final int LEVEL_2 = 5;

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
        usersDAO = new UsersDAO();
        usersDAO.setEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Test
    public void testCreateRelationship() {
        CharactersEntity character = new CharactersEntity();
        character.setName(NAME);
        character.setClazz(CLASS);
        character.setLevel(LEVEL);
        character.setRace(RACE);

        CharactersEntity character2 = new CharactersEntity();
        character2.setName(NAME_2);
        character2.setClazz(CLASS_2);
        character2.setLevel(LEVEL_2);
        character2.setRace(RACE_2);

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
        assertEquals(1, amount);
        CharactersEntity charactersEntity = usersDAO.findCharacterFromUser(user, NAME);
        assertNotNull(charactersEntity);

        usersDAO.addCharacterToUser(user, character2);
        amount = usersDAO.getCurrentCharacterAmount(USERNAME);
        assertEquals(2, amount);
        CharactersEntity charactersEntity2 = usersDAO.findCharacterFromUser(user, NAME_2);
        assertNotNull(charactersEntity2);
    }

    @Test
    public void TestRemoveRelationship() {
        UsersEntity user = usersDAO.findUser(USERNAME);
        assertNotNull(user);
        CharactersEntity character = usersDAO.findCharacterFromUser(user, NAME);
        assertNotNull(character);
        CharactersEntity character2 = usersDAO.findCharacterFromUser(user, NAME_2);
        assertNotNull(character2);

        usersDAO.removeCharacterFromUser(user, character);
        int amount = usersDAO.getCurrentCharacterAmount(USERNAME);
        assertEquals(1, amount);

        usersDAO.removeCharacterFromUser(user, character2);
        amount = usersDAO.getCurrentCharacterAmount(USERNAME);
        assertEquals(0, amount);
    }

    @AfterClass
    public static void destroyFixture() {
        usersDAO.close();
    }
}
