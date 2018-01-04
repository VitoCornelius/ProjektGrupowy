package com.example.mikolaj.newapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_statistics);

    }
}
