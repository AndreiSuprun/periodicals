package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * Class that represents user of system;
 * Each user must have one and only one role.
 *
 * @author Andrei Suprun
 * @see Role
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1864654776101421526L;
    private Long id;
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public static class Builder {
        private final User user;

        public Builder() {
            user = new User();
        }

        public Builder setId(Long id) {
            user.setId(id);
            return this;
        }

        public Builder setRole(Role role) {
            user.setRole(role);
            return this;
        }

        public Builder setFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder setLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public User build() {
            return user;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("role=" + role)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
