package com.example.mikolaj.newapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * okienko glowne
 */
public class MainActivity extends AppCompatActivity {
    public Button btnMap;
    public Button btnStatistics;
    public Button btnContact;
    public Button btnZaloguj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        btnMap = (Button ) findViewById(R.id.btn_map);
        btnStatistics = (Button) findViewById(R.id.btn_statistics);
        btnContact = (Button) findViewById(R.id.btn_contact);
        btnZaloguj = (Button) findViewById(R.id.btn_zaloguj);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, map.class);
                startActivity(toy);
            }
        });

        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent toy = new Intent(MainActivity.this, contact.class);
                // startActivity(toy);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, contact.class);
                startActivity(toy);
            }
        });

        btnZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, login.class);
                startActivity(toy);
            }
        });


    }

}
