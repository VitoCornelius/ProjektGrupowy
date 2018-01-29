package com.example.mikolaj.newapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Menu dostępne jedynie po zalogowaniu
 */
public class userMenu extends AppCompatActivity {
    Button newOffense, showOffenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().hide();

        newOffense = (Button) findViewById(R.id.btn_newOffense);
        showOffenses = (Button) findViewById(R.id.btn_db_offenses);

        newOffense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userMenu.this, addOffense.class);
                startActivity(intent);
            }
        });


        showOffenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userMenu.this, showOffenses.class);
                startActivity(intent);
            }
        });


    }
}
