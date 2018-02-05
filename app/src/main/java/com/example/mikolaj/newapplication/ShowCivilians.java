package com.example.mikolaj.newapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.InputStream;

/**
 * Created by Tomek on 2018-02-05.
 */

public class ShowCivilians extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
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
        setContentView(R.layout.activity_cyvilian_base);
        listView = findViewById(R.id.ListView1);
        btnDate = (Button) findViewById(R.id.sort1);
        btnVictims = (Button) findViewById(R.id.sort2);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        getData2();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long rowId) {


                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ShowCivilians.this);
                adb.setTitle("List");
                adb.setMessage(" selected Item is="
                        +parent.getItemAtPosition(position));
                adb.setPositiveButton("Ok", null);
                adb.show();

            }

        });

    }


    private void getData2()
    {
        data2 =  new String[DownloadDataBase.civilians.size()];
                for (int j=0; j<DownloadDataBase.civilians.size(); j++)
                {
                    data2[j] = "\nID: " + DownloadDataBase.civilians.get(j).getCivilianID()
                            + ", status: " + DownloadDataBase.civilians.get(j).getGender()
                            + ", Imie: " + DownloadDataBase.civilians.get(j).getName()
                            +", Nazwisko: " + DownloadDataBase.civilians.get(j).getSurname()
                            +", Adres:  " + DownloadDataBase.civilians.get(j).getAddress();
                }
    }
}
