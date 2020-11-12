package com.bsu.classes;

import java.util.Objects;

public class User {
    private String name;
    private String login;
    private String email;
    private String password;
    private String role;

    public User(String name, String login, String email, String password, String role) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {return name;}
    public String getLogin() {return login;}
    public String getEMail() {return email;}
    public String getPassword() {return password;}
    public String getRole() {return role;}


    @Override
    public String toString() {
        return name + ", " + login + ", " + email + ", " + password + ", " + role;
    }

    public static User createUser(String line) {
        String[] field = line.split(", ");
        String name = field[0];
        String login= field[1];
        String email = field[2];
        String password = field[3];
        if (field.length == 4) {
            User user1 = new User(name, login, email, password, "USER");
            return user1;
        }
        else {
            String role = field[4];
            User user2 = new User(name, login, email, password, role);
            return user2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, email, password, role);
    }
}