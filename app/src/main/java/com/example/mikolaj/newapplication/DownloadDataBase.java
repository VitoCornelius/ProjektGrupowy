package com.example.mikolaj.newapplication;

import com.google.android.gms.maps.model.LatLng;

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

/**
 * Created by Tomek on 2018-01-30.
 */

public class DownloadDataBase {

    static String address = "http://wilki.kylos.pl/PSI/showOffenses.php";
    static String URLDistriction = "http://wilki.kylos.pl/PSI/addDistrict.php";
    static String URLborderPoints= "http://wilki.kylos.pl/PSI/addBorderPoints.php";

    static List<offense> offenses = new ArrayList<>();
    static List<Districts> districts = new ArrayList<>();
    static List<offense> glosne_przeklinanie = new ArrayList<>();
    static List<offense> porwanie = new ArrayList<>();
    static List<offense> zabojstwo = new ArrayList<>();
    static List<offense> przemoc_domowa = new ArrayList<>();

    static private String[] data;

    static InputStream inputStream = null;
    static String line = null;
    static String result = null;

    public static void splitOffenseData(List<offense> offenses)
    {
        for (int i=0; i<offenses.size(); i++)
        {
            switch (offenses.get(i).getType())
            {
                case "1":
                {
                    glosne_przeklinanie.add(offenses.get(i));
                    break;
                }

                case "2":
                {
                    porwanie.add(offenses.get(i));
                    break;
                }

                case "3":
                {
                    zabojstwo.add(offenses.get(i));
                    break;
                }

                case "4":
                {
                    przemoc_domowa.add(offenses.get(i));
                    break;
                }

            }
        }
    }


    public static void getData1(String address){
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

            switch (address) {
                case "http://wilki.kylos.pl/PSI/showOffenses.php":{
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                offenses.add(new offense(
                        Integer.parseInt(jsonObject.getString("id_zgloszenia")),
                        Integer.parseInt(jsonObject.getString("id_funkcjonariusza")),
                        jsonObject.getString("data_zgloszenia"),
                        jsonObject.getString("id_typ_zgloszenia"),
                        jsonObject.getString("id_dzielnica"),
                        Integer.parseInt(jsonObject.getString("liczba_poszkodowanych")),
                        jsonObject.getString("opis_zdarzenia"),
                        jsonObject.getString("id_dyspozytora"),
                        "Anonim",
                        jsonObject.getString("id_status_zgloszenia"),
                        Double.parseDouble(jsonObject.getString("pozycja_gps_x")),
                        Double.parseDouble(jsonObject.getString("pozycja_gps_y"))));
                                            }
                                            break;
                        }
                case "http://wilki.kylos.pl/PSI/addDistrict.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        districts.add(new Districts(
                                (jsonObject.getString("nazwa_dzielnicy")
                                        //TODO  reszta atrybutow dzielnic, które sa w bazie
                                )));

                    }
                    break;
                }
                case "http://wilki.kylos.pl/PSI/addBorderPoints.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        for(int j=0; j<districts.size();j++)
                        {
                            if(districts.get(j).getDistrictName().equals(jsonObject.getString("nazwa_dzielnicy")))
                            {


                                districts.get(j).addListPoints(new LatLng(
                                        Double.parseDouble(jsonObject.getString("wsp_y")),
                                        Double.parseDouble(jsonObject.getString("wsp_x")))
                                );
                                break;
                            }
                        }

                    }
                    break;
                }
             }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
