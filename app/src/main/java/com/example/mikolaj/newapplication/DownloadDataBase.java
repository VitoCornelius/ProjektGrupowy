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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tomek on 2018-01-30.
 */

public class DownloadDataBase {

    static String address = "http://wilki.kylos.pl/PSI/_showOffenses.php";
    static String URLCivilians = "http://wilki.kylos.pl/PSI/_showCivilians.php";
    static String URLReportCivilianRecords = "http://wilki.kylos.pl/PSI/_showCivilianRecords.php";
    static String URLDistriction = "http://wilki.kylos.pl/PSI/_addDistrict.php";
    static String URLborderPoints= "http://wilki.kylos.pl/PSI/_addBorderPoints.php";

    static List<offense> offenses = new ArrayList<>();
    static List<Civilians> civilians = new ArrayList<>();
    static List<ReportCivilianRecords> reportCivilianRecords= new ArrayList<>();
    static List<Districts> districts = new ArrayList<>();
    static List<offense> glosne_przeklinanie = new ArrayList<>();
    static List<offense> porwanie = new ArrayList<>();
    static List<offense> zabojstwo = new ArrayList<>();
    static List<offense> przemoc_domowa = new ArrayList<>();
    static private String[] data;

    static InputStream inputStream = null;
    static String line = null;
    static String result = null;

    public static void splitRecords()
    {
        for (ReportCivilianRecords recordsList: reportCivilianRecords) {
            for (offense sthHappened: offenses) {
                if(recordsList.getReportID()==sthHappened.getOffenseId())
                {
                    sthHappened.associatedCivilianRecords.add(recordsList);
                    break;
                }
            }
        }
    }

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
                case "http://wilki.kylos.pl/PSI/_showOffenses.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        offenses.add(new offense(
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("officer_id")),
                                jsonObject.getString("date"),
                                jsonObject.getString("report_type_id"),
                                jsonObject.getString("district_name"),
                                Integer.parseInt(jsonObject.getString("victims_number")),
                                jsonObject.getString("description"),
                                jsonObject.getString("dispatcher_id"),
                                "Anonim",
                                jsonObject.getString("report_type_id"),
                                Double.parseDouble(jsonObject.getString("longitude")),
                                Double.parseDouble(jsonObject.getString("latitude")),
                                jsonObject.getString("address")));
                    }
                    break;
                }
                case "http://wilki.kylos.pl/PSI/_showCivilians.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        civilians.add(new Civilians(
                                Integer.parseInt(jsonObject.getString("civilian_id")),
                                jsonObject.getString("name"),
                                jsonObject.getString("surname"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("address")));
                    }
                    break;
                }
                case "http://wilki.kylos.pl/PSI/_showCivilianRecords.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        reportCivilianRecords.add(new ReportCivilianRecords(
                                Integer.parseInt(jsonObject.getString("rcr_id")),
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("civilian_id")),
                                jsonObject.getString("civilian_status"),
                                jsonObject.getString("description")));
                    }
                    break;
                }

                case "http://wilki.kylos.pl/PSI/_addDistrict.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        districts.add(new Districts(
                                (jsonObject.getString("district_name")
                                        //TODO  reszta atrybutow dzielnic, które sa w bazie
                                )));

                    }
                    break;
                }
                case "http://wilki.kylos.pl/PSI/_addBorderPoints.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        for(int j=0; j<districts.size();j++)
                        {
                            if(districts.get(j).getDistrictName().equals(jsonObject.getString("district_name")))
                            {

                                districts.get(j).addListPoints(new LatLng(
                                        Double.parseDouble(jsonObject.getString("latitude")),
                                        Double.parseDouble(jsonObject.getString("longitude")))
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