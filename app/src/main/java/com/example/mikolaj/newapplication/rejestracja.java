package com.example.mikolaj.newapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class rejestracja extends AppCompatActivity implements AsyncResponse, View.OnClickListener {

    EditText etUsername, etName, etSurname, etPassword,etRank;
    Spinner spDistrict;
    Button btnLogin;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "user_name";
    public static final String Name = "nameKey";
    public static String sUsername, sName, sSurname;
    public static myAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        etName = (EditText) findViewById(R.id.signup_input_name);
        etSurname = (EditText) findViewById(R.id.signup_input_surname);
        etUsername = (EditText) findViewById(R.id.signup_input_username);
        etPassword = (EditText) findViewById(R.id.signup_input_password);
        etRank = (EditText) findViewById(R.id.signup_input_stopien);
        spDistrict = (Spinner) findViewById(R.id.spinner1);
        btnLogin = (Button) findViewById(R.id.btn_signup);
        btnLogin.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "Register Successfully", Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, userMenu.class);
            sUsername = etUsername.getText().toString();
            sName = etName.getText().toString();
            sSurname = etSurname.getText().toString();
            account = new myAccount(sName, sSurname,sUsername);
            startActivity(in);
        } else {
            Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        HashMap postData = new HashMap();
        //postData.put("mobile", "android");
        postData.put("txtName", etName.getText().toString());
        postData.put("txtSurname", etSurname.getText().toString());
        postData.put("txtUsername", etUsername.getText().toString());
        postData.put("txtPassword", etPassword.getText().toString());
        postData.put("txtRank", etRank.getText().toString());
        postData.put("txtDistrict", spDistrict.getSelectedItem().toString());
        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
        task.execute("http://wilki.kylos.pl/PSI/_register.php");
    }

    public void login(View view) {
    }
}