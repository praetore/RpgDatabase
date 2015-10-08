package io.github.praetore.rpgdb.models;

import javax.persistence.*;

/**
 * Created by darryl on 7-10-15.
 */
@Entity
@Table(name = "servers", schema = "public", catalog = "rpgdb")
public class ServersEntity {
    private String address;
    private String name;
    private String location;
    private short maxUsers;
    private short connectedUsers;

    @Id
    @Column(name = "address", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "location", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "max_users", nullable = false, insertable = true, updatable = true)
    public short getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(short maxUsers) {
        this.maxUsers = maxUsers;
    }

    @Basic
    @Column(name = "connected_users", nullable = false, insertable = true, updatable = true)
    public short getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(short connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServersEntity that = (ServersEntity) o;

        if (maxUsers != that.maxUsers) return false;
        if (connectedUsers != that.connectedUsers) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (int) maxUsers;
        result = 31 * result + (int) connectedUsers;
        return result;
    }
}
