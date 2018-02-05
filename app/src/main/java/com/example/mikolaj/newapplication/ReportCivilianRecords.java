package com.example.mikolaj.newapplication;

/**
 * Created by Tomek on 2018-02-04.
 */

public class ReportCivilianRecords {

    private int rcrID;  // id zgloszenia
    private int reportID;  // id
    private int civilianID;  // id
    private String civilianStatus;    // data - do zmiany na format date
    private String description;    // typ zgloszenia

    public ReportCivilianRecords(int rcrID, int reportID, int civilianID, String civilianStatus, String description) {
        this.rcrID = rcrID;
        this.reportID = reportID;
        this.civilianID = civilianID;
        this.civilianStatus = civilianStatus;
        this.description = description;
    }

    /*public String getCivilianNameByID()
    {
        for (Civilians civilians: DownloadDataBase.civilians
             ) {
            if(civilianID==civilians.getCivilianID())
                return civilians.getName()
        }

    }*/

    public int getRcrID() {
        return rcrID;
    }

    public void setRcrID(int rcrID) {
        this.rcrID = rcrID;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getCivilianID() {
        return civilianID;
    }

    public void setCivilianID(int civilianID) {
        this.civilianID = civilianID;
    }

    public String getCivilianStatus() {
        return civilianStatus;
    }

    public void setCivilianStatus(String civilianStatus) {
        this.civilianStatus = civilianStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
