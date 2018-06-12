package com.example.mikolaj.newapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class addOffense extends AppCompatActivity implements AsyncResponse, View.OnClickListener {

    EditText etAddress;
    EditText etDescription;
    EditText etCount;
    EditText etLatitude;
    EditText etLongitude;
    EditText sDistrict;

    Button btnAdd, btnLoc;
    Spinner sType,etNrD;
    String temp1,temp2;
    Double dTemp1, dTemp2;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "offense";

    public static String DistrictName;

    public static String findDistrict(double latitude, double longitude, List<Districts> listOfDistricts){

        LatLng point = new LatLng(latitude,longitude);
        for (Districts x : listOfDistricts)
        {
            if (Districts.isPointInPolygon(point,x.getList())){
                addOffense.DistrictName= x.districtName;
                return x.districtName;
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_offense);
        etAddress = findViewById(R.id.address);
        etLatitude = findViewById(R.id.poz_x);
        etLongitude = findViewById(R.id.poz_y);
        etDescription = findViewById(R.id.input_description);
        etCount = findViewById(R.id.signup_input_count);
        etNrD = findViewById(R.id.signup_input_dyspozytor);
        btnAdd = findViewById(R.id.btn_signup);
        sDistrict = findViewById(R.id.dzielnica);
        sType = findViewById(R.id.spinner4);
        btnLoc = findViewById(R.id.btn_loc);
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
                postData.put("txtID", myAccount.getMyID());

                postData.put("txtLatitude", etLatitude.getText().toString());
                postData.put("txtLongitute", etLongitude.getText().toString());
                postData.put("txtAddress", etAddress.getText().toString());
                postData.put("txtDescription", etDescription.getText().toString());
                postData.put("txtCount", etCount.getText().toString());
                postData.put("txtNrD", etNrD.getSelectedItem().toString());
                postData.put("txtDistrict", sDistrict.getText().toString());
                postData.put("txtType", sType.getSelectedItem().toString());

                PostResponseAsyncTask task = new PostResponseAsyncTask(this, postData);
                task.execute("http://wilki.kylos.pl/PSI/_addOffense.php");
                break;
        }
    }

//    public void logout(View view) {
//        SharedPreferences sharedpreferences = getSharedPreferences(login.MyPREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.clear();
//        editor.commit();
//    }
//
//    public void close(View view) {
//        finish();
//    }

    @Override
    public void processFinish(String result) {
        if (result.equals("success")) {
            StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
            Toast.makeText(this, "Zgłoszenie zostało dodane", Toast.LENGTH_LONG).show();
            DownloadDataBase.getData1(DownloadDataBase.address);
            DownloadDataBase.splitOffenseData(DownloadDataBase.offenses);
            Intent in = new Intent(this, userMenu.class);
            finish();
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
                String foundDistrict = addOffense.findDistrict(dTemp1,dTemp2, DownloadDataBase.districts);
                sDistrict.setText( foundDistrict );
                if(dialog.isShowing()){
                    dialog.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}