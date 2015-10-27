package io.github.praetore.rpgdb;

import io.github.praetore.rpgdb.daos.UsersDAO;
import io.github.praetore.rpgdb.models.CharactersEntity;
import io.github.praetore.rpgdb.models.UsersEntity;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.*;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by darryl on 7-10-15.
 */
public class TestUserCharacterRelationshipPersistence {
    private static UsersDAO usersDAO;
    private static final String PERSISTENCE_UNIT_NAME = "rpgUnit";

    private static final String NAME = "Ulamog";
    private static final String RACE = "Orc";
    private static final String CLASS = "Warrior";
    private static final int LEVEL = 1;

    private static final String NAME_2 = "Dedelore";
    private static final String RACE_2 = "Troll";
    private static final String CLASS_2 = "Rogue";
    private static final int LEVEL_2 = 5;

    private static final String NAME_3 = "Blippy";
    private static final String RACE_3 = "Elf";
    private static final String CLASS_3 = "Healer";
    private static final int LEVEL_3 = 7;

    private static final String USERNAME = "Praetore";
    private static final String USERNAME2 = "Emperor";
    private static final int BALANCE = 100;
    private static final String FIRSTNAME = "Darryl";
    private static final String LASTNAME = "Amatsetam";
    private static final String IBAN = "123456789";
    private static final short CHARACTERSLOTS = (short) 2;
    private static final short MONTHSPAYED = (short) 0;
    private static final String PASSWORD = "verysecret";
    private static final boolean ISBANNED = false;

    @BeforeClass
    public static void initFixture() {
        Properties properties = new Properties();
        properties.setProperty(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML,
                "META-INF/persistence-test.xml");
        usersDAO = new UsersDAO();
        usersDAO.setEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
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
        assertNotNull(user);

        usersDAO.loginUser(USERNAME, PASSWORD);
        boolean loggedIn = usersDAO.isLoggedIn(USERNAME);
        assertTrue(loggedIn);

        int amount = usersDAO.getCurrentCharacterAmount(USERNAME);
        assertEquals(0, amount);

        short slotsRemaining = user.getCharacterSlots();
        assertEquals(2, slotsRemaining);

        usersDAO.addCharacterToUser(user, character);

        CharactersEntity charactersEntity = usersDAO.findCharacterFromUser(user, NAME);
        assertNotNull(charactersEntity);

        slotsRemaining = user.getCharacterSlots();
        assertEquals(1, slotsRemaining);

        amount = usersDAO.getCurrentCharacterAmount(USERNAME);
        assertEquals(1, amount);

        usersDAO.addCharacterToUser(user, character2);

        CharactersEntity charactersEntity2 = usersDAO.findCharacterFromUser(user, NAME_2);
        assertNotNull(charactersEntity2);

        amount = usersDAO.getCurrentCharacterAmount(USERNAME);
        assertEquals(2, amount);

        slotsRemaining = user.getCharacterSlots();
        assertEquals(0, slotsRemaining);
    }

    @Test
    public void TestRemoveRelationship() {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserName(USERNAME2);
        usersEntity.setBalance(BALANCE);
        usersEntity.setFirstName(FIRSTNAME);
        usersEntity.setLastName(LASTNAME);
        usersEntity.setIban(IBAN);
        usersEntity.setCharacterSlots(CHARACTERSLOTS);
        usersEntity.setMonthsPayed(MONTHSPAYED);
        usersEntity.setPassword(PASSWORD);
        usersEntity.setBanned(ISBANNED);

        CharactersEntity character = new CharactersEntity();
        character.setName(NAME_3);
        character.setClazz(CLASS_3);
        character.setLevel(LEVEL_3);
        character.setRace(RACE_3);

        usersDAO.createNewUser(usersEntity);

        usersDAO.loginUser(USERNAME2, PASSWORD);
        assertTrue(usersDAO.isLoggedIn(USERNAME2));

        usersDAO.addCharacterToUser(usersEntity, character);

        UsersEntity user = usersDAO.findUser(USERNAME2);
        assertNotNull(user);

        int amount = usersDAO.getCurrentCharacterAmount(USERNAME2);
        assertEquals(1, amount);

        character = usersDAO.findCharacterFromUser(user, NAME_3);
        assertNotNull(character);

        usersDAO.removeCharacterFromUser(user, character);

        amount = usersDAO.getCurrentCharacterAmount(USERNAME2);
        assertEquals(0, amount);
    }

    @AfterClass
    public static void destroyFixture() {
        usersDAO.close();
    }
}
