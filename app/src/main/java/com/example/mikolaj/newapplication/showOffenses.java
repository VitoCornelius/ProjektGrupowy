package com.example.mikolaj.newapplication;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    String address = "http://192.168.64.2/client/showOffenses.php";
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
                data[i]="ID: " + jsonObject.getString("id_zgloszenia")
                        + ", ID funkcjonariusza " + jsonObject.getString("id_funkcjonariusza")
                +
                        ", Data zgłoszenia: " + jsonObject.getString("data_zgloszenia") +", Typ zgłoszenia: " + jsonObject.getString("id_typ_zgloszenia")
                        +
                        ", Dzielnica: " +jsonObject.getString("id_dzielnica") +", Opis:: "+ jsonObject.getString("opis_zdarzenia")
                        +
                        ", Liczba poszkodowanych: " +jsonObject.getString("liczba_poszkodowanych");
                //+
                //        ", ID Dyspozytora: " +jsonObject.getString("id_dyspoztora") + ", Status zgłoszenia: "+ jsonObject.getString("id_status_zgloszenia") ;
                        //+
                //        ", X: " +jsonObject.getString("pozycja_gps_x") + ", Y: " + jsonObject.getString("pozycja_gps_y");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
