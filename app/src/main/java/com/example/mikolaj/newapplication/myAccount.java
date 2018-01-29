package com.example.mikolaj.newapplication;

/**
 * Created by Mikolaj on 19.12.2017.
 */

public class myAccount {
    private String name;
    private String surname;
    private String username;

    private boolean login;

    public myAccount(String name) {
        this.name = name;
        this.login = true;
    }

    public myAccount(String name, String surname, String username) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.login = true;
    }

    public String getName() {
        return name;
    }
}
