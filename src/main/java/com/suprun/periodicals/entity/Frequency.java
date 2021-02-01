package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.util.StringJoiner;
/**
 * Class that represents frequency of periodical
 *
 * @author Andrei Suprun
 * @see Periodical
 */
public class Frequency implements Serializable {

    private static final long serialVersionUID = -7403178438448003318L;

    private Integer id;
    private String name;
    private int value;
    private String description;

    public static class Builder {
        private final Frequency frequency;

        public Builder() {
            frequency = new Frequency();
        }

        public Builder setId(Integer id) {
            frequency.setId(id);
            return this;
        }

        public Builder setName(String name) {
            frequency.setName(name);
            return this;
        }

        public Builder setValue(int value) {
            frequency.setValue(value);
            return this;
        }


        public Builder setDescription(String description) {
            frequency.setDescription(description);
            return this;
        }

        public Frequency build() {
            return frequency;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Frequency() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frequency frequency = (Frequency) o;

        if (value != frequency.value) return false;
        if (id != null ? !id.equals(frequency.id) : frequency.id != null) return false;
        if (name != null ? !name.equals(frequency.name) : frequency.name != null) return false;
        return description != null ? description.equals(frequency.description) : frequency.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + value;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Frequency.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("value='" + value + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
