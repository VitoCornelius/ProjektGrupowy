package com.example.mikolaj.newapplication;

/**
 * Created by Mikolaj on 19.12.2017.
 */

public class myAccount {
    private String name;
    private boolean login;

    public myAccount(String name) {
        this.name = name;
        this.login = true;
    }

    public String getName() {
        return name;
    }
}
