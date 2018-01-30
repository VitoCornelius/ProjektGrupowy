package com.example.mikolaj.newapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class addOffense extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
    EditText etID, etName, etSurname, etAddress, etDescription, etCount, etNrD,etLatitude, etLongitude;
    Button btnAdd, btnLoc;
    Spinner sDistrict, sType;
    String temp1,temp2;
    Double dTemp1, dTemp2;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "offense";
    public static final String Name = "nameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_offense);
        etID = (EditText) findViewById(R.id.input_ID);
        etAddress = (EditText) findViewById(R.id.address);
        etLatitude = (EditText) findViewById(R.id.poz_x);
        etLongitude = (EditText) findViewById(R.id.poz_y);
        etDescription = (EditText) findViewById(R.id.input_description);
        etCount = (EditText) findViewById(R.id.signup_input_count);
        etNrD = (EditText) findViewById(R.id.signup_input_dyspozytor);
        btnAdd = (Button) findViewById(R.id.btn_signup);
        sDistrict = (Spinner) findViewById(R.id.spinner3);
        sType = (Spinner) findViewById(R.id.spinner4);
        btnLoc = (Button) findViewById(R.id.btn_loc);
        btnAdd.setOnClickListener(this);
        btnLoc.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_loc:
                String lokalizacja = null;
                lokalizacja = etAddress.getText().toString();
                new Coordinates().execute(lokalizacja.replace(" ","+"));
                break;
            case R.id.btn_signup:
                Calendar c = Calendar.getInstance();
                System.out.println("Current time =&gt; "+c.getTime());
                HashMap postData = new HashMap();
                postData.put("txtID", etID.getText().toString());
                postData.put("txtLatitude", etLatitude.getText().toString());
                postData.put("txtLongitude", etLongitude.getText().toString());
                postData.put("txtAddress", etAddress.getText().toString());
                postData.put("txtDescription", etDescription.getText().toString());
                postData.put("txtCount", etCount.getText().toString());
                postData.put("txtNrD", etNrD.getText().toString());
                postData.put("txtDistrict", sDistrict.getSelectedItem().toString());
                postData.put("txtType", sType.getSelectedItem().toString());


                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute("http://wilki.kylos.pl/PSI/addOffense.php");
                break;
        }


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


    private class Coordinates extends AsyncTask<String,Void,String>{
        ProgressDialog dialog = new ProgressDialog(addOffense.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                 String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%S", address);
                 response = http.getHTTPData(url);
                 return response;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                String lon = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
                etLatitude.setText(String.format("%s",lat));
                etLongitude.setText(String.format("%s",lon));
                temp1 = lat;
                dTemp1 = Double.parseDouble(temp1);
                temp2 = lon;
                dTemp2 = Double.parseDouble(temp2);

                if(dialog.isShowing()){
                    dialog.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
