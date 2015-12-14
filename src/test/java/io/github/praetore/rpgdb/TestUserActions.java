package io.github.praetore.rpgdb;

import io.github.praetore.rpgdb.daos.UsersDAO;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by darryl on 5-11-15.
 */
public class TestUserActions {
    private static UsersDAO usersDAO;
    private static final String PERSISTENCE_UNIT_NAME = "rpgUnit";

    private static final String USERNAME = "Praetore";
    private static final String USERNAME2 = "Emperor";
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
    public void testUserLogin() throws Exception {

    }

    @Test
    public void testSubscriptionUpdate() throws Exception {

    }

    @Test
    public void testBalanceUpdate() throws Exception {

    }

    @AfterClass
    public static void destroyFixture() {
        usersDAO.close();
    }
}
