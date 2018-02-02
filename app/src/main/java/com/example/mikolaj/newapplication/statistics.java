package com.example.mikolaj.newapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.mikolaj.newapplication.DownloadDataBase.inputStream;

public class statistics extends AppCompatActivity {
    int przeklinanie=0,porwanie=0,zabojstwo=0,przemoc=0;
    int iloscZdarzenia[] = new int[4];
    String zdarzenia[] = {"Głośne przeklinanie","Porwania","Zabojstwa","Przemoc domowa"};
    public void add(){

        przeklinanie=DownloadDataBase.glosne_przeklinanie.size();
        porwanie=DownloadDataBase.porwanie.size();
        zabojstwo=DownloadDataBase.zabojstwo.size();
        przemoc=DownloadDataBase.przemoc_domowa.size();
        iloscZdarzenia[0]=przeklinanie;
        iloscZdarzenia[1]=porwanie;
        iloscZdarzenia[2]=zabojstwo;
        iloscZdarzenia[3]=przemoc;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_statistics);
        //DownloadDataBase.getData1(DownloadDataBase.address);
        //DownloadDataBase.splitOffenseData(DownloadDataBase.offenses);
        add();
        setupPieChart();
}

    private void setupPieChart() {
         List<PieEntry> pieEntryList = new ArrayList<>();
         for(int i=0;i<iloscZdarzenia.length;i++){
             pieEntryList.add(new PieEntry(iloscZdarzenia[i],zdarzenia[i] ));
         }
        PieDataSet dataSet = new PieDataSet(pieEntryList,"Zdarzenia");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS) ;
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();
    }




}
