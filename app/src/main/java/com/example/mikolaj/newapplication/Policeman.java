package com.example.mikolaj.newapplication;

public class Policeman {
    public String name;
    public int phoneNumber;
    public double position_longitude;  //dlugosc_geograficzna
    public double position_latitude;  //dlugosc_geograficzna


    public Policeman(String name, int phoneNumber, double position_latitude,double position_longitude) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position_longitude = position_longitude;
        this.position_latitude = position_latitude;
    }
}
