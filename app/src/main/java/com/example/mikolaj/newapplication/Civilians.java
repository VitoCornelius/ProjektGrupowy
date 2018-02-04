package com.example.mikolaj.newapplication;

/**
 * Created by Tomek on 2018-02-04.
 */

public class Civilians {

    private int civilianID;  //
    private String name;    //
    private String surname;    //
    private String gender;    //
    private String address;    //

    public Civilians(int civilianID, String name, String surname, String gender, String address) {
        this.civilianID = civilianID;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.address = address;
    }

    public int getCivilianID() {
        return civilianID;
    }

    public void setCivilianID(int civilianID) {
        this.civilianID = civilianID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
