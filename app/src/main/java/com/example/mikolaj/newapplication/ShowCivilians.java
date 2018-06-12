package com.example.mikolaj.newapplication;

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

/**
 * Created by Tomek on 2018-02-05.
 */
public class ShowCivilians extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    Button btnDate;
    Button btnVictims;
    Button btnAddCivilian;

    String result = null;
    String[] data2;
    int iID =0;
    String name, surname,address,gender;
    String sID;
    String sID2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyvilian_base);
        listView = findViewById(R.id.ListView1);
        btnDate = findViewById(R.id.sort1);
        btnVictims = findViewById(R.id.sort2);
        btnAddCivilian = findViewById(R.id.addPerson);

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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long rowId) {

                sID = null;
                sID2 = null;
                final AlertDialog.Builder adb = new AlertDialog.Builder(
                        ShowCivilians.this);
                sID = parent.getItemAtPosition(position).toString();
                sID2 = Character.toString(sID.charAt(5));
                if(!Character.toString(sID.charAt(6)).equals(",")){
                    sID2+=Character.toString(sID.charAt(6));
                    if(!Character.toString(sID.charAt(7)).equals(",")){
                        sID2+=Character.toString(sID.charAt(7));
                    }
                }
                iID = Integer.parseInt(sID2);
                name=null;
                address=null;
                surname=null;
                gender=null;

                for(int i = 0;i<DownloadDataBase.civilians.size();i++){
                    if(DownloadDataBase.civilians.get(i).getCivilianID()==iID){
                        name = DownloadDataBase.civilians.get(i).getName();
                        address = DownloadDataBase.civilians.get(i).getAddress();
                        surname = DownloadDataBase.civilians.get(i).getSurname();
                        gender = DownloadDataBase.civilians.get(i).getGender();
                    }
                }

                adb.setTitle(gender);
                adb.setMessage("Imię: " + name + "\nNazwisko: " + surname + "\nAdres: " + address);
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

    public void itemClicked2(View view)
    {
        Intent toy = new Intent(ShowCivilians.this, addCivilianRecords.class);
        startActivity(toy);
    }

    private void getData2()
    {
        data2 =  new String[DownloadDataBase.civilians.size()];
        for (int j=0; j<DownloadDataBase.civilians.size(); j++)
        {
            data2[j] = "\nID: " + DownloadDataBase.civilians.get(j).getCivilianID()
                    + ", Imie: " + DownloadDataBase.civilians.get(j).getName()
                    +", Nazwisko: " + DownloadDataBase.civilians.get(j).getSurname();
        }
    }
}
