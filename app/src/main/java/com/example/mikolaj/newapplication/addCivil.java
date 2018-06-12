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

/**
 * Created by Tomek on 2018-02-05.
 */
public class addCivil extends AppCompatActivity implements AsyncResponse, View.OnClickListener {

    EditText etAddress;
    EditText etName;
    EditText etSurname;
    Spinner spGender;
    Button btnLogin;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "user_name";
    public static String sName;
    public static String sSurname;
    public static String sAddress;
    public static String sGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_civil);
        etName = findViewById(R.id.signup_input_person_name);
        etSurname = findViewById(R.id.signup_input_person_surname);
        etAddress = findViewById(R.id.signup_input_person_address);
        spGender = findViewById(R.id.spinner2);
        btnLogin = findViewById(R.id.btn_signup_person);
        btnLogin.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "Register Successfully", Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, userMenu.class);
            sGender = spGender.getSelectedItem().toString();
            sAddress = etAddress.getText().toString();
            sName = etName.getText().toString();
            sSurname = etSurname.getText().toString();
            DownloadDataBase.civilians.add(new Civilians(DownloadDataBase.civilians.get(DownloadDataBase.civilians.size()-1).getCivilianID()+1,
                    sName,sSurname,sGender,sAddress));
            startActivity(in);
        } else {
            Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View view) {
        HashMap postData = new HashMap();
        postData.put("txtName", etName.getText().toString());
        postData.put("txtSurname", etSurname.getText().toString());
        postData.put("txtGender", spGender.getSelectedItem().toString());
        postData.put("txtAddress", etAddress.getText().toString());

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
        task.execute("http://wilki.kylos.pl/PSI/_registerCivilians.php");
    }

    public void login(View view) {
    }
}