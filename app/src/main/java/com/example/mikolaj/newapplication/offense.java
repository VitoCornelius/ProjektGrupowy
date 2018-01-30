package com.example.mikolaj.newapplication;

/**
 * Created by Mikolaj on 19.12.2017.
 */

public class offense {

    private int offenseId;  // id zgloszenia
    private int officerId;  // id funkcjonariusza
    private String date;    // data - do zmiany na format date
    private String type;    // typ zgloszenia
    private String district;    // dzielnica
    private int victims;        // liczba poszkodowanych
    private String description; // opis zgloszenia
    private String dispatcher;  //dyspozytor
    private String reportingPerson; // osoba zglaszajaca
    private String status; // status zgloszenia
    private double position_longitude;  //dlugosc_geograficzna
    private double position_latitude;  //dlugosc_geograficzna



    public offense(int offenseId, int officerId, String date, String type,
                   String district, int victims, String description, String dispatcher,
                   String reportingPerson, String status, double position_latitude, double position_longitude) {
        this.offenseId = offenseId;
        this.officerId = officerId;
        this.date = date;
        this.type = type;
        this.district = district;
        this.victims = victims;
        this.description = description;
        this.dispatcher = dispatcher;
        this.status = status;
        this.position_latitude = position_latitude;
        this.position_longitude = position_longitude;
    }

    public int getOffenseId() {
        return offenseId;
    }

    public void setOffenseId(int offenseId) {
        this.offenseId = offenseId;
    }

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getVictims() {
        return victims;
    }

    public void setVictims(int victims) {
        this.victims = victims;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public String getReportingPerson() {
        return reportingPerson;
    }

    public void setReportingPerson(String reportingPerson) {
        this.reportingPerson = reportingPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPosition_longitude() {
        return position_longitude;
    }

    public void setPosition_longitude(double position_longitude) {
        this.position_longitude = position_longitude;
    }

    public double getPosition_latitude() {
        return position_latitude;
    }

    public void setPosition_latitude(double position_latitude) {
        this.position_latitude = position_latitude;
    }

    public boolean sendOffense() {
        // TODO - wysylanie obiektu do bazy danych
        return true;
    }
}
