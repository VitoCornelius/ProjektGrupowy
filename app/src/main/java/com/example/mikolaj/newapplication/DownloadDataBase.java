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
    static String sortByDateASC = "http://wilki.kylos.pl/PSI/_showOffensesByDateASC.php";
    static String sortByDateDESC = "http://wilki.kylos.pl/PSI/_showOffensesByDateDESC.php";
    static String sortByVictimsASC = "http://wilki.kylos.pl/PSI/_showOffensesByVictimsASC.php";
    static String sortByVictimsDESC = "http://wilki.kylos.pl/PSI/_showOffensesByVictimsDESC.php";
    static String sortByTypeASC = "http://wilki.kylos.pl/PSI/_showOffensesByStatusASC.php";
    static String sortByTypeDESC = "http://wilki.kylos.pl/PSI/_showOffensesByStatusDESC.php";

    static List<offense> offenses = new ArrayList<>();

    static List<offense> offensesByDateASC = new ArrayList<>();
    static List<offense> offensesByDateDESC = new ArrayList<>();
    static List<offense> offensesByVictimsASC = new ArrayList<>();
    static List<offense> offensesByVictimsDESC = new ArrayList<>();
    static List<offense> offensesByTypeASC = new ArrayList<>();
    static List<offense> offensesByTypeDESC = new ArrayList<>();

    static List<Policeman> policemanList = new ArrayList<>();



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
    static {
        policemanList.add(new Policeman("Nash Bridghes", 691342810, 54.505612,18.491115));
        policemanList.add(new Policeman("Jackie Chan", 123456789, 54.439377,18.567191));
        policemanList.add(new Policeman("Komisarz Rex", 123456789, 54.405890,18.601477));
        policemanList.add(new Policeman("Ojciec Mateusz", 123456789, 54.389201,18.588173));
        policemanList.add(new Policeman("Detektyw Cobretti", 123456789, 54.500359,18.507902));
    }

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
                                jsonObject.getString("report_status"),
                                Double.parseDouble(jsonObject.getString("longitude")),
                                Double.parseDouble(jsonObject.getString("latitude")),
                                jsonObject.getString("address")));
                    }
                    break;
                }

                case "http://wilki.kylos.pl/PSI/_showOffensesByDateASC.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        offensesByDateASC.add(new offense(
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("officer_id")),
                                jsonObject.getString("date"),
                                jsonObject.getString("report_type_id"),
                                jsonObject.getString("district_name"),
                                Integer.parseInt(jsonObject.getString("victims_number")),
                                jsonObject.getString("description"),
                                jsonObject.getString("dispatcher_id"),
                                "Anonim",
                                jsonObject.getString("report_status"),
                                Double.parseDouble(jsonObject.getString("longitude")),
                                Double.parseDouble(jsonObject.getString("latitude")),
                                jsonObject.getString("address")));
                    }
                    break;
                }

                case "http://wilki.kylos.pl/PSI/_showOffensesByDateDESC.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        offensesByDateDESC.add(new offense(
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("officer_id")),
                                jsonObject.getString("date"),
                                jsonObject.getString("report_type_id"),
                                jsonObject.getString("district_name"),
                                Integer.parseInt(jsonObject.getString("victims_number")),
                                jsonObject.getString("description"),
                                jsonObject.getString("dispatcher_id"),
                                "Anonim",
                                jsonObject.getString("report_status"),
                                Double.parseDouble(jsonObject.getString("longitude")),
                                Double.parseDouble(jsonObject.getString("latitude")),
                                jsonObject.getString("address")));
                    }
                    break;
                }
                case "http://wilki.kylos.pl/PSI/_showOffensesByVictimsASC.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        offensesByVictimsASC.add(new offense(
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("officer_id")),
                                jsonObject.getString("date"),
                                jsonObject.getString("report_type_id"),
                                jsonObject.getString("district_name"),
                                Integer.parseInt(jsonObject.getString("victims_number")),
                                jsonObject.getString("description"),
                                jsonObject.getString("dispatcher_id"),
                                "Anonim",
                                jsonObject.getString("report_status"),
                                Double.parseDouble(jsonObject.getString("longitude")),
                                Double.parseDouble(jsonObject.getString("latitude")),
                                jsonObject.getString("address")));
                    }
                    break;
                }
                case "http://wilki.kylos.pl/PSI/_showOffensesByVictimsDESC.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        offensesByVictimsDESC.add(new offense(
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("officer_id")),
                                jsonObject.getString("date"),
                                jsonObject.getString("report_type_id"),
                                jsonObject.getString("district_name"),
                                Integer.parseInt(jsonObject.getString("victims_number")),
                                jsonObject.getString("description"),
                                jsonObject.getString("dispatcher_id"),
                                "Anonim",
                                jsonObject.getString("report_status"),
                                Double.parseDouble(jsonObject.getString("longitude")),
                                Double.parseDouble(jsonObject.getString("latitude")),
                                jsonObject.getString("address")));
                    }
                    break;
                }

                case "http://wilki.kylos.pl/PSI/_showOffensesByStatusASC.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        offensesByTypeASC.add(new offense(
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("officer_id")),
                                jsonObject.getString("date"),
                                jsonObject.getString("report_type_id"),
                                jsonObject.getString("district_name"),
                                Integer.parseInt(jsonObject.getString("victims_number")),
                                jsonObject.getString("description"),
                                jsonObject.getString("dispatcher_id"),
                                "Anonim",
                                jsonObject.getString("report_status"),
                                Double.parseDouble(jsonObject.getString("longitude")),
                                Double.parseDouble(jsonObject.getString("latitude")),
                                jsonObject.getString("address")));
                    }
                    break;
                }
                case "http://wilki.kylos.pl/PSI/_showOffensesByStatusDESC.php":{
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        offensesByTypeDESC.add(new offense(
                                Integer.parseInt(jsonObject.getString("report_id")),
                                Integer.parseInt(jsonObject.getString("officer_id")),
                                jsonObject.getString("date"),
                                jsonObject.getString("report_type_id"),
                                jsonObject.getString("district_name"),
                                Integer.parseInt(jsonObject.getString("victims_number")),
                                jsonObject.getString("description"),
                                jsonObject.getString("dispatcher_id"),
                                "Anonim",
                                jsonObject.getString("report_status"),
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