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
    private String description; // opis zgloszenia
    private String dispatcher;  //dyspozytor
    private String reportingPerson; // osoba zglaszajaca
    private int status; // status zgloszenia


    public offense(int offenseId, int officerId, String date, String type, String district, String description, String dispatcher, String reportingPerson, int status) {
        this.offenseId = offenseId;
        this.officerId = officerId;
        this.date = date;
        this.type = type;
        this.district = district;
        this.description = description;
        this.dispatcher = dispatcher;
        this.reportingPerson = reportingPerson;
        this.status = status;
    }

    public boolean sendOffense() {
        // TODO - wysylanie obiektu do bazy danych
        return true;
    }
}
