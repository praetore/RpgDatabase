package io.github.praetore.rpgdb.daos;

import io.github.praetore.rpgdb.models.CharactersEntity;
import io.github.praetore.rpgdb.models.Subscription;
import io.github.praetore.rpgdb.models.UsersEntity;

import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        Boolean authorized = isLoggedIn(userName);
        if (user != null && !authorized) {
            if (user.getPassword().equals(password)) {
                this.loggedIn.put(userName, true);
            }
        }
    }

    public boolean isLoggedIn(String userName) {
        Boolean exist = this.loggedIn.get(userName);
        if (exist == null) {
            this.loggedIn.put(userName, false);
        }
        return this.loggedIn.get(userName);
    }

    public CharactersEntity findCharacterFromUser(UsersEntity usersEntity, String characterName) {
        Boolean authorized = isLoggedIn(usersEntity.getUserName());
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
        String userName = usersEntity.getUserName();
        Boolean authorized = isLoggedIn(userName);
        boolean canAddCharacter = getAvailableCharacterSlots(userName) > 0;
        if (authorized && canAddCharacter) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                usersEntity.getCharacters().add(charactersEntity);
                usersEntity.setCharacterSlots((short) (usersEntity.getCharacterSlots() - 1));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public void removeCharacterFromUser(UsersEntity usersEntity, CharactersEntity character) {
        Boolean authorized = isLoggedIn(usersEntity.getUserName());
        if (authorized) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                usersEntity.getCharacters().remove(character);
                usersEntity.setCharacterSlots((short) (usersEntity.getCharacterSlots() + 1));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public int getAvailableCharacterSlots(String username) {
        Boolean authorized = isLoggedIn(username);
        if (authorized) {
            return findUser(username).getCharacterSlots();
        }
        return 0;
    }

    public int getCurrentCharacterAmount(String userName) {
        Boolean authorized = isLoggedIn(userName);
        if (authorized) {
            return findUser(userName).getCharacters().size();
        }
        return 0;
    }

    private void removeUser(UsersEntity user) {
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
        Boolean authorized = isLoggedIn(userName);
        if (authorized) {
            UsersEntity user = findUser(userName);
            removeUser(user);
        }
    }

    public void updateBalance(String userName, int amount) {
        Boolean authorized = isLoggedIn(userName);
        boolean canUpdateBalance = sufficientBalanceLeft(userName, amount);
        if (authorized && canUpdateBalance) {
            UsersEntity user = findUser(userName);
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                user.setBalance(user.getBalance() + amount);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public void updateAvailableSlots(String userName, int slotUpdate) {
        Boolean authorized = isLoggedIn(userName);
        int slotPrice = 1;
        int totalPrice = slotUpdate * slotPrice;
        boolean sufficient = sufficientBalanceLeft(userName, -1 * totalPrice);
        if (sufficient && authorized) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                UsersEntity user = findUser(userName);

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDate dNow = LocalDate.now();
                String dNowF = df.format(dNow);
                Timestamp lastPaymentDate = Timestamp.valueOf(dNowF);

                user.setLastPayment(lastPaymentDate);
                user.setBalance(user.getBalance() - totalPrice);
                user.setCharacterSlots((short) (user.getCharacterSlots() + slotUpdate));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public boolean sufficientBalanceLeft(String userName, int amount) {
        return findUser(userName).getBalance() + amount >= 0;
    }

    public void updateSubscription(String userName, Subscription subscription) {
        Boolean authorized = isLoggedIn(userName);
        int amount = subscription.getCost();
        boolean canUpdateBalance = sufficientBalanceLeft(userName, -amount);

        if (authorized && canUpdateBalance) {
            EntityTransaction transaction = getEntityManager().getTransaction();
            try {
                transaction.begin();
                UsersEntity user = findUser(userName);

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDate dNow = LocalDate.now();
                String dNowF = df.format(dNow);
                Timestamp tsNow = Timestamp.valueOf(dNowF);

                int monthsAdd = subscription.getMonths();

                user.setLastPayment(tsNow);
                user.setBalance(user.getBalance() - amount);
                user.setMonthsPayed((short) (user.getMonthsPayed() + monthsAdd));
                user.setCharacterSlots((short) (user.getCharacterSlots() + 5));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

    public List<String> whoIsLoggedIn() {
        List<String> loggedInUsers = new ArrayList<String>();
        for (Map.Entry<String, Boolean> loggedInUser : loggedIn.entrySet()) {
            if (loggedInUser.getValue()) {
                loggedInUsers.add(loggedInUser.getKey());
            }
        }
        return loggedInUsers;
    }
}
