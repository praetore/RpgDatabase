package io.github.praetore.rpgdb.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by darryl on 7-10-15.
 */
@Entity
@Table(name = "users", schema = "public", catalog = "rpgdb")
public class UsersEntity {
    private String userName;
    private int balance;
    private String firstName;
    private String lastName;
    private String iban;
    private short characterSlots;
    private Timestamp lastPayment;
    private short monthsPayed;
    private String password;
    private boolean banned;
    private List<CharactersEntity> characters;

    @Id
    @Column(name = "user_name", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "owns",
            joinColumns = @JoinColumn(name="user_name_users"),
            inverseJoinColumns = @JoinColumn(name = "name_characters"))
    public List<CharactersEntity> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharactersEntity> characters) {
        this.characters = characters;
    }

    @Basic
    @Column(name = "balance", nullable = false, insertable = true, updatable = true)
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "first_name", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "iban", nullable = true, insertable = true, updatable = true, length = 10485760)
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Basic
    @Column(name = "character_slots", nullable = false, insertable = true, updatable = true)
    public short getCharacterSlots() {
        return characterSlots;
    }

    public void setCharacterSlots(short characterSlots) {
        this.characterSlots = characterSlots;
    }

    @Basic
    @Column(name = "last_payment", nullable = true, insertable = true, updatable = true)
    public Timestamp getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(Timestamp lastPayment) {
        this.lastPayment = lastPayment;
    }

    @Basic
    @Column(name = "months_payed", nullable = false, insertable = true, updatable = true)
    public short getMonthsPayed() {
        return monthsPayed;
    }

    public void setMonthsPayed(short monthsPayed) {
        this.monthsPayed = monthsPayed;
    }

    @Basic
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "banned", nullable = false, insertable = true, updatable = true)
    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (balance != that.balance) return false;
        if (characterSlots != that.characterSlots) return false;
        if (monthsPayed != that.monthsPayed) return false;
        if (banned != that.banned) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (iban != null ? !iban.equals(that.iban) : that.iban != null) return false;
        if (lastPayment != null ? !lastPayment.equals(that.lastPayment) : that.lastPayment != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + balance;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (iban != null ? iban.hashCode() : 0);
        result = 31 * result + (int) characterSlots;
        result = 31 * result + (lastPayment != null ? lastPayment.hashCode() : 0);
        result = 31 * result + (int) monthsPayed;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (banned ? 1 : 0);
        return result;
    }
}
