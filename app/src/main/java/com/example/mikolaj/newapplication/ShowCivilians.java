package com.example.mikolaj.newapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by Tomek on 2018-02-05.
 */

public class ShowCivilians extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    Button btnDate, btnVictims, btnType, btnAddCivilian;

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
        btnAddCivilian = (Button) findViewById(R.id.addPerson);

        if(DownloadDataBase.offenses.size()==0)
        {
            DownloadDataBase.getData1(DownloadDataBase.address);
            DownloadDataBase.getData1(DownloadDataBase.URLDistriction);
            DownloadDataBase.getData1(DownloadDataBase.URLborderPoints);
            DownloadDataBase.splitOffenseData(DownloadDataBase.offenses);
            DownloadDataBase.getData1(DownloadDataBase.URLCivilians);
            DownloadDataBase.getData1(DownloadDataBase.URLReportCivilianRecords);
            DownloadDataBase.getData1(DownloadDataBase.sortByDateASC);
            DownloadDataBase.getData1(DownloadDataBase.sortByDateDESC);
            DownloadDataBase.getData1(DownloadDataBase.sortByTypeASC);
            DownloadDataBase.getData1(DownloadDataBase.sortByTypeDESC);
            DownloadDataBase.getData1(DownloadDataBase.sortByVictimsASC);
            DownloadDataBase.getData1(DownloadDataBase.sortByVictimsDESC);
            DownloadDataBase.splitRecords();
        }

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


    public void itemClicked(View view)
    {
        Intent toy = new Intent(ShowCivilians.this, addCivil.class);
        startActivity(toy);
    }



    private void getData2()
    {
        data2 =  new String[DownloadDataBase.civilians.size()];
        for (int j=0; j<DownloadDataBase.civilians.size(); j++)
        {
            data2[j] = "\nID: " + DownloadDataBase.civilians.get(j).getCivilianID()
                    + ", Płeć: " + DownloadDataBase.civilians.get(j).getGender()
                    + ", Imie: " + DownloadDataBase.civilians.get(j).getName()
                    +", Nazwisko: " + DownloadDataBase.civilians.get(j).getSurname()
                    +", Adres:  " + DownloadDataBase.civilians.get(j).getAddress();
        }
    }
}
