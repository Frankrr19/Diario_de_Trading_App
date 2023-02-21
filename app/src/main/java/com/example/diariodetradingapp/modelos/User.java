package com.example.diariodetradingapp.modelos;

public class User {
    private String name;
    private String Lastname;
    private String email;

    public User(String name, String lastname, String email) {
        this.name = name;
        Lastname = lastname;
        this.email = email;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
