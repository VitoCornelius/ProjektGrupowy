package com.example.mikolaj.newapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addOffense extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
    EditText etID, etName, etSurname, etAddress, etDescription, etCount, etNrD,etLatitude, etLongitude;
    Button btnAdd;
    Spinner sDistrict, sType;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "offense";
    public static final String Name = "nameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_offense);
        etID = (EditText) findViewById(R.id.input_ID);
        etLatitude = (EditText) findViewById(R.id.szerGeog);
        etLongitude = (EditText) findViewById(R.id.DlGeog);
        etDescription = (EditText) findViewById(R.id.input_description);
        etCount = (EditText) findViewById(R.id.signup_input_count);
        etNrD = (EditText) findViewById(R.id.signup_input_dyspozytor);
        btnAdd = (Button) findViewById(R.id.btn_signup);
        sDistrict = (Spinner) findViewById(R.id.spinner3);
        sType = (Spinner) findViewById(R.id.spinner4);
        btnAdd.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public void onClick(View view) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        // Now formattedDate have current date/time


        HashMap postData = new HashMap();
        postData.put("txtID", etID.getText().toString());
        postData.put("txtLatitude", etLatitude.getText().toString());
        postData.put("txtLongitude", etLongitude.getText().toString());
        postData.put("txtDescription", etDescription.getText().toString());
        postData.put("txtCount", etCount.getText().toString());
        postData.put("txtNrD", etNrD.getText().toString());
        postData.put("txtDistrict", sDistrict.getSelectedItem().toString());
        postData.put("txtType", sType.getSelectedItem().toString());

        PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
        task.execute("http://192.168.64.2/client/addOffense.php");

    }


    public void logout(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(login.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void close(View view) {
        finish();
    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            Toast.makeText(this, "Zgłoszenie zostało dodane", Toast.LENGTH_LONG).show();
            Intent in = new Intent(this, userMenu.class);
            startActivity(in);
        } else {
            Toast.makeText(this, "Register Failed", Toast.LENGTH_LONG).show();

        }
    }
}
