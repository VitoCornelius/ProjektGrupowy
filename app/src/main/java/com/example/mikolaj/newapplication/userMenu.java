package com.example.mikolaj.newapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Menu dostępne jedynie po zalogowaniu
 */
public class userMenu extends AppCompatActivity {
    Button newOffense, showOffenses, showMap,btnLogout, btnCivilians;
    public Button btnStatistics;
    public static boolean goToStatistics=false;
    public static boolean goToAddOffense=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().hide();

        newOffense = (Button) findViewById(R.id.btn_newOffense);
        showOffenses = (Button) findViewById(R.id.btn_db_offenses);
        showMap = (Button) findViewById(R.id.btn_map);
        btnStatistics = (Button) findViewById(R.id.btn_statistics);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        btnCivilians = (Button) findViewById(R.id.btn_db_cyvils);
//        newOffense.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(userMenu.this, addOffense.class);
//                startActivity(intent);
//            }
//        });

        newOffense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(userMenu.this, addOffense.class);
                startActivity(toy);
            }
        });


        showOffenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userMenu.this, showOffenses.class);
                startActivity(intent);
            }
        });

        btnCivilians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userMenu.this, ShowCivilians.class);
                startActivity(intent);
            }
        });

        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(userMenu.this, statistics.class);
                startActivity(toy);
            }
        });


        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(userMenu.this, map.class);
                startActivity(toy);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(userMenu.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "Wylogowano!", Toast.LENGTH_LONG).show();
                startActivity(toy);
            }
        });


    }
}