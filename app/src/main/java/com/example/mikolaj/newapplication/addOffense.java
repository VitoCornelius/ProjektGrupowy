package com.example.mikolaj.newapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class addOffense extends AppCompatActivity {
    Button bu, bu2;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_offense);

        bu=(Button)findViewById(R.id.button2);
        bu2=(Button)findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.abc);
        textView.setText(login.account.getName());


    }

    public  void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(login.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void close(View view){
        finish();
    }
}
