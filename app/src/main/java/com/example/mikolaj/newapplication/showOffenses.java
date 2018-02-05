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
import android.widget.Button;
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
import java.util.List;

public class showOffenses extends AppCompatActivity{

    ListView listView;
    ArrayAdapter<String> adapter;
    String address = "http://wilki.kylos.pl/PSI/_showOffenses.php";
    Button btnDate, btnVictims, btnType;

    InputStream inputStream = null;
    String line = null;
    String result = null;
    String[] data;
    String[] data2;
    String sID = null;
    String sID2= null;
    int iID = 0;
    String description, adres, repPers, sType, sStatus;
    boolean datePressed, victimsPressed, typePressed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offenses);
        listView = findViewById(R.id.ListView1);
        btnDate = (Button) findViewById(R.id.sort1);
        btnVictims = (Button) findViewById(R.id.sort2);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        getData2(0);
        show();


    }

    private void show(){
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long rowId) {


                AlertDialog.Builder adb = new AlertDialog.Builder(
                        showOffenses.this);
                sID = parent.getItemAtPosition(position).toString();
                sID2 = Character.toString(sID.charAt(4));
                if(!Character.toString(sID.charAt(5)).equals(",")){
                    sID2+=Character.toString(sID.charAt(5));
                    if(!Character.toString(sID.charAt(6)).equals(",")){
                        sID2+=Character.toString(sID.charAt(6));
                    }
                }
                iID = Integer.parseInt(sID2);
                description=null;
                adres=null;
                repPers=null;
                sType=null;
                sStatus = null;
                for(int i = 0;i<DownloadDataBase.offenses.size();i++){
                    if(DownloadDataBase.offenses.get(i).getOffenseId()==iID){
                        description = DownloadDataBase.offenses.get(i).getDescription();
                        adres = DownloadDataBase.offenses.get(i).getAddress();
                        sType = DownloadDataBase.offenses.get(i).getTypeConverted();
                        sStatus = DownloadDataBase.offenses.get(i).getStatusConverted();
                    }
                }

                adb.setTitle(sType);
                adb.setMessage("Adres: " + adres + "\nOpis: " + description + "\nStatus: " +  sStatus);
                adb.setPositiveButton("Ok", null);
                adb.show();

            }

        });
    }

    private void getData2(int n)
    {

        data2 =  new String[DownloadDataBase.offenses.size()];
        switch (n){
            case 0:
                for (int j=0; j<DownloadDataBase.offenses.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offenses.get(j).getOffenseId()
                            +", Data: " + DownloadDataBase.offenses.get(j).getDate()
                            + ", Poszkodowani: " + DownloadDataBase.offenses.get(j).getVictims();
                }

                show();
                break;
            case 1:
                for (int j=0; j<DownloadDataBase.offensesByDateASC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByDateASC.get(j).getOffenseId()
                            +", Data: " + DownloadDataBase.offensesByDateASC.get(j).getDate()
                            + ", Poszkodowani: " + DownloadDataBase.offensesByDateASC.get(j).getVictims();

                }
                show();
                break;
            case 2:
                for (int j=0; j<DownloadDataBase.offensesByDateDESC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByDateDESC.get(j).getOffenseId()
                            +", Data: " + DownloadDataBase.offensesByDateDESC.get(j).getDate()
                    + ", Poszkodowani: " + DownloadDataBase.offensesByDateDESC.get(j).getVictims();

                }
                show();
                break;
            case 5:
                for (int j=0; j<DownloadDataBase.offensesByVictimsASC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByVictimsASC.get(j).getOffenseId()
                            +", Data: " + DownloadDataBase.offensesByVictimsASC.get(j).getDate()
                            + ", Poszkodowani: " + DownloadDataBase.offensesByVictimsASC.get(j).getVictims();

                }
                show();
                break;
            case 6:
                for (int j=0; j<DownloadDataBase.offensesByVictimsDESC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByVictimsDESC.get(j).getOffenseId()
                            +", Data: " + DownloadDataBase.offensesByVictimsDESC.get(j).getDate()
                    + ", Poszkodowani: " + DownloadDataBase.offensesByVictimsDESC.get(j).getVictims();

                }
                show();
                break;
        }

    }


    public void showVictims(View view) {
        data2 = null;
        if(victimsPressed){
            getData2(6);
            victimsPressed=false;
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);
            listView.setAdapter(adapter);

        }else{
            getData2(5);
            victimsPressed=true;
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);
            listView.setAdapter(adapter);

        }
    }
    public void showDate(View view) {
        data2=null;
        if(datePressed){
            getData2(2);
            datePressed = false;
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);

            listView.setAdapter(adapter);

        }else{
            getData2(1);
            datePressed = true;
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);

            listView.setAdapter(adapter);

        }
    }


}