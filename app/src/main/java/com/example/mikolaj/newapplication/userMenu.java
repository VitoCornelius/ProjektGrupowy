package com.example.mikolaj.newapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userMenu extends AppCompatActivity {
    Button newOffense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().hide();



        newOffense = (Button) findViewById(R.id.btn_newOffense);

        newOffense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userMenu.this, addOffense.class);
                startActivity(intent);
            }
        });



    }
}
