package com.example.mikolaj.newapplication;

import android.app.Application;

/**
 * Created by Mikolaj on 19.12.2017.
 */

public class MyApp extends Application {
    private String myState;

    public String getMyState() {
        return myState;
    }

    public void setMyState(String myState) {
        this.myState = myState;
    }
}
