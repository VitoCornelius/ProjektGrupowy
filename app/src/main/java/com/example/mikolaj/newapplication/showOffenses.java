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
    boolean datePressed, victimsPressed, typePressed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offenses);
        listView = findViewById(R.id.ListView1);
        btnDate = (Button) findViewById(R.id.sort1);
        btnType = (Button) findViewById(R.id.sort3);
        btnVictims = (Button) findViewById(R.id.sort2);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        getData2(0);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);
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


    private void getData2(int n)
    {

        data2 =  new String[DownloadDataBase.offenses.size()];
        switch (n){
            case 0:
                for (int j=0; j<DownloadDataBase.offenses.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offenses.get(j).getOffenseId()
                            +", Data zgłoszenia: " + DownloadDataBase.offenses.get(j).getDate()
                            +", Typ zgłoszenia: " + DownloadDataBase.offenses.get(j).getType()
                            +", Opis: "+ DownloadDataBase.offenses.get(j).getDescription()
                            + ", Status zgłoszenia: "+ DownloadDataBase.offenses.get(j).getStatus()
                            +", Adres: " + DownloadDataBase.offenses.get(j).getAddress()
                            + ", Powiązani: " + DownloadDataBase.offenses.get(j).civilanReportString();
                }
                break;
            case 1:
                for (int j=0; j<DownloadDataBase.offensesByDateASC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByDateASC.get(j).getOffenseId()
                            +", Data zgłoszenia: " + DownloadDataBase.offensesByDateASC.get(j).getDate()
                            +", Typ zgłoszenia: " + DownloadDataBase.offensesByDateASC.get(j).getType()
                            +", Opis: "+ DownloadDataBase.offensesByDateASC.get(j).getDescription()
                            + ", Status zgłoszenia: "+ DownloadDataBase.offensesByDateASC.get(j).getStatus()
                            +", Adres: " + DownloadDataBase.offensesByDateASC.get(j).getAddress()
                            + ", Powiązani: " + DownloadDataBase.offensesByDateASC.get(j).civilanReportString();
                }
                break;
            case 2:
                for (int j=0; j<DownloadDataBase.offensesByDateDESC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByDateDESC.get(j).getOffenseId()
                            +", Data zgłoszenia: " + DownloadDataBase.offensesByDateDESC.get(j).getDate()
                            +", Typ zgłoszenia: " + DownloadDataBase.offensesByDateDESC.get(j).getType()
                            +", Opis: "+ DownloadDataBase.offensesByDateDESC.get(j).getDescription()
                            + ", Status zgłoszenia: "+ DownloadDataBase.offensesByDateDESC.get(j).getStatus()
                            +", Adres: " + DownloadDataBase.offensesByDateDESC.get(j).getAddress()
                            + ", Powiązani: " + DownloadDataBase.offensesByDateDESC.get(j).civilanReportString();
                }
                break;
            case 3:
                for (int j=0; j<DownloadDataBase.offensesByTypeASC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByTypeASC.get(j).getOffenseId()
                            +", Data zgłoszenia: " + DownloadDataBase.offensesByTypeASC.get(j).getDate()
                            +", Typ zgłoszenia: " + DownloadDataBase.offensesByTypeASC.get(j).getType()
                            +", Opis: "+ DownloadDataBase.offensesByTypeASC.get(j).getDescription()
                            + ", Status zgłoszenia: "+ DownloadDataBase.offensesByTypeASC.get(j).getStatus()
                            +", Adres: " + DownloadDataBase.offensesByTypeASC.get(j).getAddress()
                            + ", Powiązani: " + DownloadDataBase.offensesByTypeASC.get(j).civilanReportString();
                }
                break;
            case 4:
                for (int j=0; j<DownloadDataBase.offensesByTypeDESC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByTypeDESC.get(j).getOffenseId()
                            +", Data zgłoszenia: " + DownloadDataBase.offensesByTypeDESC.get(j).getDate()
                            +", Typ zgłoszenia: " + DownloadDataBase.offensesByTypeDESC.get(j).getType()
                            +", Opis: "+ DownloadDataBase.offensesByTypeDESC.get(j).getDescription()
                            + ", Status zgłoszenia: "+ DownloadDataBase.offensesByTypeDESC.get(j).getStatus()
                            +", Adres: " + DownloadDataBase.offensesByTypeDESC.get(j).getAddress()
                            + ", Powiązani: " + DownloadDataBase.offensesByTypeDESC.get(j).civilanReportString();
                }
                break;
            case 5:
                for (int j=0; j<DownloadDataBase.offensesByVictimsASC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByVictimsASC.get(j).getOffenseId()
                            +", Data zgłoszenia: " + DownloadDataBase.offensesByVictimsASC.get(j).getDate()
                            +", Typ zgłoszenia: " + DownloadDataBase.offensesByVictimsASC.get(j).getType()
                            +", Opis: "+ DownloadDataBase.offensesByVictimsASC.get(j).getDescription()
                            + ", Status zgłoszenia: "+ DownloadDataBase.offensesByVictimsASC.get(j).getStatus()
                            +", Adres: " + DownloadDataBase.offensesByVictimsASC.get(j).getAddress()
                            + ", Powiązani: " + DownloadDataBase.offensesByVictimsASC.get(j).civilanReportString();
                }
                break;
            case 6:
                for (int j=0; j<DownloadDataBase.offensesByVictimsDESC.size(); j++)
                {
                    data2[j]="ID: " + DownloadDataBase.offensesByVictimsDESC.get(j).getOffenseId()
                            +", Data zgłoszenia: " + DownloadDataBase.offensesByVictimsDESC.get(j).getDate()
                            +", Typ zgłoszenia: " + DownloadDataBase.offensesByVictimsDESC.get(j).getType()
                            +", Opis: "+ DownloadDataBase.offensesByVictimsDESC.get(j).getDescription()
                            + ", Status zgłoszenia: "+ DownloadDataBase.offensesByVictimsDESC.get(j).getStatus()
                            +", Adres: " + DownloadDataBase.offensesByVictimsDESC.get(j).getAddress()
                            + ", Powiązani: " + DownloadDataBase.offensesByVictimsDESC.get(j).civilanReportString();
                }
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
    public void showType(View view) {
        data2=null;
        if(typePressed){
            getData2(4);
            typePressed = false;
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);

            listView.setAdapter(adapter);

        }else{
            getData2(3);
            typePressed = true;
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);

            listView.setAdapter(adapter);

        }
    }


//    private void getData(String address){
//        try {
//            URL url = new URL(address);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            inputStream = new BufferedInputStream(connection.getInputStream());
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        //READ is content into a strong
//        try{
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            while((line = bufferedReader.readLine())!=null){
//                stringBuilder.append(line+"\n");
//            }
//            inputStream.close();
//            result = stringBuilder.toString();
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        //PARSE JSON DATA
//
//        try{
//            JSONArray jsonArray = new JSONArray(result);
//            JSONObject jsonObject = null;
//
//            data =  new String[jsonArray.length()];
//            for(int i=0;i<jsonArray.length();i++){
//                jsonObject = jsonArray.getJSONObject(i);
//                String type = null;
//
//                switch (jsonObject.getString("report_type_id"))
//                {
//                    case "1":
//                        type = "Głośne przeklinanie";
//                        break;
//                    case "2":
//                        type = "Porwanie";
//                        break;
//                    case "3":
//                        type = "Zabójstwo";
//                        break;
//                    case "4":
//                        type = "Przemoc domowa";
//                        break;
//                }
//                data[i]="ID: " + jsonObject.getString("report_id")
//                        +", Data zgłoszenia: " + jsonObject.getString("date")
//                        +", Typ zgłoszenia: " + type
//                        +", Opis: "+ jsonObject.getString("description")
//                        + ", Status zgłoszenia: "+ jsonObject.getString("report_status")
//                        +", Adres: " + jsonObject.getString("address");
//            }
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
}