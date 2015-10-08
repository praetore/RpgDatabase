package io.github.praetore.rpgdb.models;

import javax.persistence.*;

/**
 * Created by darryl on 7-10-15.
 */
@Entity
@Table(name = "characters", schema = "public", catalog = "rpgdb")
public class CharactersEntity {
    private String name;
    private String clazz;
    private String race;
    private int level;

    @Id
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "class", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Basic
    @Column(name = "race", nullable = false, insertable = true, updatable = true, length = 10485760)
    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    @Basic
    @Column(name = "level", nullable = false, insertable = true, updatable = true)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharactersEntity that = (CharactersEntity) o;

        if (level != that.level) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
        if (race != null ? !race.equals(that.race) : that.race != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        result = 31 * result + (race != null ? race.hashCode() : 0);
        result = 31 * result + level;
        return result;
    }
}
