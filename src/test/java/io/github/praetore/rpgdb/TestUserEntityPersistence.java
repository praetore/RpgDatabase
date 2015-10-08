package io.github.praetore.rpgdb;

import io.github.praetore.rpgdb.daos.UsersDAO;
import io.github.praetore.rpgdb.models.UsersEntity;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Created by darryl on 2-10-15.
 */
public class TestUserEntityPersistence {
    private static UsersDAO usersDAO;
    private static final String PERSISTENCE_UNIT_NAME = "rpgUnit";

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
        Properties properties = new Properties();
        properties.setProperty(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML,
                "META-INF/persistence-test.xml");
        usersDAO = new UsersDAO();
        usersDAO.setEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
    }

    @Test
    public void testUserCreation() {
        UsersEntity entity = new UsersEntity();
        entity.setUserName(USERNAME);
        entity.setBalance(BALANCE);
        entity.setFirstName(FIRSTNAME);
        entity.setLastName(LASTNAME);
        entity.setIban(IBAN);
        entity.setCharacterSlots(CHARACTERSLOTS);
        entity.setMonthsPayed(MONTHSPAYED);
        entity.setPassword(PASSWORD);
        entity.setBanned(ISBANNED);

        usersDAO.createNewUser(entity);
        UsersEntity user = usersDAO.findUser(USERNAME);
        assertNotNull(user);

        String userName = user.getUserName();
        assertEquals(userName, USERNAME);
        int balance = user.getBalance();
        assertEquals(balance, BALANCE);
        String firstName = user.getFirstName();
        assertEquals(firstName, FIRSTNAME);
        String lastName = user.getLastName();
        assertEquals(lastName, LASTNAME);
        String iban = user.getIban();
        assertEquals(iban, IBAN);
        short characterSlots = user.getCharacterSlots();
        assertEquals(characterSlots, CHARACTERSLOTS);
        short monthsPayed = user.getMonthsPayed();
        assertEquals(monthsPayed, MONTHSPAYED);
        String password = user.getPassword();
        assertEquals(password, PASSWORD);
        boolean banned = user.isBanned();
        assertEquals(ISBANNED, banned);
    }

    @Test
    public void TestUserRemoval() {
        UsersEntity entity = new UsersEntity();
        entity.setUserName(USERNAME);
        entity.setBalance(BALANCE);
        entity.setFirstName(FIRSTNAME);
        entity.setLastName(LASTNAME);
        entity.setIban(IBAN);
        entity.setCharacterSlots(CHARACTERSLOTS);
        entity.setMonthsPayed(MONTHSPAYED);
        entity.setPassword(PASSWORD);
        entity.setBanned(ISBANNED);

        usersDAO.createNewUser(entity);
        UsersEntity user = usersDAO.findUser(USERNAME);
        assertNotNull(user);

        usersDAO.removeUser(USERNAME);
        user = usersDAO.findUser(USERNAME);
        assertNull(user);
    }

    @AfterClass
    public static void destroyFixture() {
        usersDAO.close();
    }
}
