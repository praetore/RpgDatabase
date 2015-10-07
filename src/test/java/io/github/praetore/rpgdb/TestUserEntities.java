package io.github.praetore.rpgdb;

import io.github.praetore.rpgdb.beans.UserServiceBean;
import io.github.praetore.rpgdb.models.UsersEntity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by darryl on 2-10-15.
 */
public class TestUserEntities {
    private static UserServiceBean serviceBean;
    private static final String PERSISTENCE_UNIT_NAME = "rpgUnit";

    @BeforeClass
    public static void initFixture() throws Exception {
        serviceBean = new UserServiceBean();
        serviceBean.setEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Test
    public void testUserEntityPersistence() throws Exception {
        UsersEntity entity = new UsersEntity();
        entity.setUserName("Praetore");
        entity.setBalance(100);
        entity.setFirstName("Darryl");
        entity.setLastName("Amatsetam");
        entity.setIban("123456789");
        entity.setCharacterSlots((short) 1);
        entity.setMonthsPayed((short) 0);
        entity.setPassword("verysecret");
        entity.setBanned(false);

        serviceBean.createUser(entity);
        UsersEntity user = serviceBean.findUser("Praetore");

        String userName = user.getUserName();
        assertEquals(userName, "Praetore");
        int balance = user.getBalance();
        assertEquals(balance, 100);
        String firstName = user.getFirstName();
        assertEquals(firstName, "Darryl");
        String lastName = user.getLastName();
        assertEquals(lastName, "Amatsetam");
        String iban = user.getIban();
        assertEquals(iban, "123456789");
        short characterSlots = user.getCharacterSlots();
        assertEquals(characterSlots, (short) 1);
        short monthsPayed = user.getMonthsPayed();
        assertEquals(monthsPayed, (short) 0);
        String password = user.getPassword();
        assertEquals(password, "verysecret");
        boolean banned = user.isBanned();
        assertFalse(banned);
    }

    @AfterClass
    public static void destroyFixture() throws Exception {
        serviceBean.close();
    }
}
