package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * Class that represents category of periodicals
 *
 * @author Andrei Suprun
 * @see Periodical
 */
public class PeriodicalCategory implements Serializable {
    private static final long serialVersionUID = 8627358865975742191L;

    private Integer id;
    private String name;
    private String description;

    public static class Builder {
        private final PeriodicalCategory periodicalCategory;

        public Builder() {
            periodicalCategory = new PeriodicalCategory();
        }

        public Builder setId(Integer id) {
            periodicalCategory.setId(id);
            return this;
        }

        public Builder setName(String name) {
            periodicalCategory.setName(name);
            return this;
        }

        public Builder setDescription(String description) {
            periodicalCategory.setDescription(description);
            return this;
        }

        public PeriodicalCategory build() {
            return periodicalCategory;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public PeriodicalCategory() {
    }

    public PeriodicalCategory(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

        PeriodicalCategory that = (PeriodicalCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PeriodicalCategory.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
