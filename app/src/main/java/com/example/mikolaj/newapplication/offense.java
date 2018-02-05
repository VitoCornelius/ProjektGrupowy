package com.example.mikolaj.newapplication;

import java.util.ArrayList;
import java.util.List;

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
    private String address;  //
    public List<ReportCivilianRecords> associatedCivilianRecords= new ArrayList<>();

    @Override
    public String toString() {
        return
                "Data zgłoszenia:'" + date + '\'' +
                ", \nDzielnica:'" + district + '\'' +
                ", \nLiczba poszkodowanych:" + victims +
                ", \nOpis zdarzenia:'" + description + '\'' +
                ", \nStatus zgłoszenia:'" + getStatusConverted() + '\'';
    }


    public offense(int offenseId, int officerId, String date, String type,
                   String district, int victims, String description, String dispatcher,
                   String reportingPerson, String status, double position_latitude, double position_longitude,
                   String address) {
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
        this.address=address;
    }

    public String civilanReportString()
    {
        String text="";
        for (ReportCivilianRecords records: this.associatedCivilianRecords)
         {
             text = text + "\nID osoby: " + records.getCivilianID()
                     + ", status: " + records.getCivilianStatus()
                     + ", Imie: " + DownloadDataBase.civilians.get(records.getCivilianID()).getName()
                     +", Nazwisko: " + DownloadDataBase.civilians.get(records.getCivilianID()).getSurname()
                     +", Adres:  " + DownloadDataBase.civilians.get(records.getCivilianID()).getAddress();
        }
        return text;
    }

    public int getOffenseId() {
        return offenseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTypeConverted() {
        String typeConverted = null;
        switch (type){
            case "1":
                typeConverted = "Głośne przeklinanie";
                break;
            case "2":
                typeConverted = "Porwanie";
                break;
            case "3":
                typeConverted = "Zabojstwo";
                break;
            case "4":
                typeConverted = "Przemoc domowa";
                break;
        }
        return typeConverted;
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
    public String getStatusConverted(){
        String statusConverted = null;
        switch (status){
            case "0" :
                statusConverted = "Nierozpatrzone";
                break;
            case "1":
                statusConverted = "Przyjęte";
                break;
            case "2":
                statusConverted = "Rozpatrzone";
                break;
        }
        return statusConverted;
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
