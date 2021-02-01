package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.util.StringJoiner;
/**
 * Class that represents publisher of periodicals
 *
 * @author Andrei Suprun
 * @see Periodical
 */
public class Publisher implements Serializable {

    private static final long serialVersionUID = -8782091490451523912L;

    private Long id;
    private String name;

    public Publisher() {
    }

    public Publisher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Publisher publisher = (Publisher) o;

        if (id != null ? !id.equals(publisher.id) : publisher.id != null) return false;
        return name != null ? name.equals(publisher.name) : publisher.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Publisher.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
