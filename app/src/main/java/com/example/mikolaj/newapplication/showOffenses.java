package com.example.mikolaj.newapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class showOffenses extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String address = "http://wilki.kylos.pl/PSI/_showOffenses.php";
    InputStream inputStream = null;
    String line = null;
    String result = null;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offenses);
        listView = findViewById(R.id.ListView1);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        getData();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long rowId) {


                AlertDialog.Builder adb = new AlertDialog.Builder(
                        showOffenses.this);
                adb.setTitle("List");
                adb.setMessage(" selected Item is="
                        +parent.getItemAtPosition(position));
                adb.setPositiveButton("Ok", null);
                adb.show();

            }

        });

    }


    private void getData(){
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            inputStream = new BufferedInputStream(connection.getInputStream());

        }catch(Exception e){
            e.printStackTrace();
        }

        //READ is content into a strong
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");
            }
            inputStream.close();
            result = stringBuilder.toString();

        }catch(Exception e){
            e.printStackTrace();
        }

        //PARSE JSON DATA

        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = null;

            data =  new String[jsonArray.length()];
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                String type = null;

                switch (jsonObject.getString("report_type_id"))
                {
                    case "1":
                        type = "Głośne przeklinanie";
                        break;
                    case "2":
                        type = "Porwanie";
                        break;
                    case "3":
                        type = "Zabójstwo";
                        break;
                    case "4":
                        type = "Przemoc domowa";
                        break;
                }
                data[i]="ID: " + jsonObject.getString("report_id")
                        +", Data zgłoszenia: " + jsonObject.getString("date")
                        +", Typ zgłoszenia: " + type
                        +", Opis: "+ jsonObject.getString("description")
                        + ", Status zgłoszenia: "+ jsonObject.getString("report_status")
                        +", Adres: " + jsonObject.getString("address");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}