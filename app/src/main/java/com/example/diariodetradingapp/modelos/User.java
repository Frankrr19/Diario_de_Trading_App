package com.example.diariodetradingapp.modelos;

public class User {
    private String name;
    private String lastname;
    private String email;
    private Float initialCash;

    public User(String name, String lastname, String email, Float initialCash) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.initialCash = initialCash;
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
        return lastname;
    }

    public void setLastname(String lastname) {
        lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getInitialCash() {
        return initialCash;
    }

    public void setInitialCash(Float initialCash) {
        this.initialCash = initialCash;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
