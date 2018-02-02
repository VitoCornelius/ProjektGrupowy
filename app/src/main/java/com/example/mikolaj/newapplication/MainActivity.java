package com.example.mikolaj.newapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * okienko glowne
 */
public class MainActivity extends AppCompatActivity {
    public Button btnMap;
    public Button btnStatistics;
    public Button btnContact;
    public Button btnZaloguj;
    public Button btnZarejestruj;
    public static boolean goToStatistics=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        btnMap = (Button) findViewById(R.id.btn_map);
        btnStatistics = (Button) findViewById(R.id.btn_statistics);
        btnContact = (Button) findViewById(R.id.btn_contact);
        btnZaloguj = (Button) findViewById(R.id.btn_zaloguj);
        btnZarejestruj = (Button) findViewById(R.id.btn_zarejestruj);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, map.class);
                goToStatistics=false;
                userMenu.goToAddOffense=false;
                startActivity(toy);
            }
        });

        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, map.class);
                goToStatistics=true;
                startActivity(toy);
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

        btnZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(MainActivity.this, rejestracja.class);
                startActivity(toy);
            }
        });


    }

}
