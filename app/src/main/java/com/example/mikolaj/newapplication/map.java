/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.mikolaj.newapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.geometry.Point;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;





import static android.app.PendingIntent.getActivity;

//import android.location.LocationListener;
//import com.google.android.gms.location.LocationListener;

/**
 * This shows how to create a simple activity with a map and a marker on the map.
 */
public class map extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private ArrayList<Polygon> polygonList = new ArrayList<>();
    private Set<offense> customOffenses = new HashSet<>();
    //private Set<offense> custom = new HashSet<>();
    private GoogleMap mMap;
    private GoogleMap mMapTemp;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker curreLocationMarker;
    Button bMorderstwa,bPorwania, bPrzeklinanie, bPrzemoc,bShow, bCall;
    private boolean isMapColor = false;
    private boolean isPressedB_zabojstwo = false;
    private boolean isPressedB_porwania = false;
    private boolean isPressedB_przemoc = false;
    private boolean isPressedB_przeklenstwa = false;
    private ArrayList<PolylineOptions> polylinesToAdd=new ArrayList<>();

    private int tempChosenCap = 100000;
    private int counter=0;
    public int tempCounter=0;

    public static final int REQUEST_LOCATION_CODE = 99;

    private PolylineOptions shortestDistance = new PolylineOptions();
    private Double shortestRoute = 100000000000000000d;

    private ArrayList<MarkerOptions> policeman = new ArrayList<>();/*= {
            new MarkerOptions().position(new LatLng(DownloadDataBase.policemanList.get(0)
                    .position_latitude,DownloadDataBase.policemanList.get(0).position_longitude))
                    .title(DownloadDataBase.policemanList.get(0).name+" Tel:"+
                    DownloadDataBase.policemanList.get(0).phoneNumber),
            new MarkerOptions().position(new LatLng(54.439377,18.567191)).title("Policjant: Jackie Chan Tel: 123456789"),
            new MarkerOptions().position(new LatLng(54.405890,18.601477)).title("Policjant: Komisarz Rex Tel: 123456789"),
            new MarkerOptions().position(new LatLng(54.389201,18.588173)).title("Policjant: Ojciec Mateusz Tel: 123456789"),
            new MarkerOptions().position(new LatLng(54.500359,18.507902)).title("Policjant: Detektyw Cobretti Tel: 123456789")};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));


        MapsInitializer.initialize(getApplicationContext());
        BitmapDescriptorFactory bitmapDescriptorFactory;
        createPolicemanMarkers();
        for(int i=0; i<policeman.size();i++) {
            policeman.get(i).icon(BitmapDescriptorFactory.defaultMarker(215));
        }

        bMorderstwa = (Button) findViewById(R.id.B_morderstwa);
        bPorwania = (Button) findViewById(R.id.B_porwania);
        bPrzeklinanie = (Button) findViewById(R.id.B_przeklinanie);
        bPrzemoc = (Button) findViewById(R.id.B_przemoc);
        bShow = (Button) findViewById(R.id.B_showD);
        bCall = (Button) findViewById(R.id.B_zadzwon);




        setContentView(R.layout.activity_map); // ustawienie widoku na jakiś taki z xml

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); // pobranie tej klasy w widoku
        mapFragment.getMapAsync(this); // pobranie async tej mapy


        // Zoom in the Google Map
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 5);
//        mMap.animateCamera();


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else    //permission denied
                {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG)
                            .show();
                }
        }

    }

    private void createPolicemanMarkers()
    {
        for (int i=0; i<DownloadDataBase.policemanList.size(); i++)
        {
            policeman.add(new MarkerOptions().position(new LatLng(DownloadDataBase.policemanList.get(i)
                    .position_latitude,DownloadDataBase.policemanList.get(i).position_longitude))
                    .title(DownloadDataBase.policemanList.get(i).name+" Tel:"+
                            DownloadDataBase.policemanList.get(i).phoneNumber));
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setOnMapClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                // Flip the red, green and blue components of the polygon's stroke color.
//                polygon.setStrokeColor(polygon.getStrokeColor() ^ 0x00ffffff);
//                polygon.setFillColor(polygon.getFillColor()^ 0x00ffffff);
//                polygon.setStrokeColor(0x800000FF);
//                polygon.setFillColor(0x800000FF);
                String description = null;
                int counter = 0;

                int PRZEKLINANIE = 0;
                int PORAWANIE = 0;
                int ZABOJSTWO = 0;
                int PRZEMOC_DOMOWA = 0;

                for (int i = 0; i < DownloadDataBase.districts.size(); i++) {
                    if (DownloadDataBase.districts.get(i).getPolygon().equals(polygon)) {
                        description = DownloadDataBase.districts.get(i).getDistrictName();
                    }
                }
                for (int i = 0; i < DownloadDataBase.offenses.size(); i++) {
                    if (DownloadDataBase.offenses.get(i).getDistrict().equals(description)) {
                        counter++;
                    }
                }

                for (int i = 0; i < DownloadDataBase.glosne_przeklinanie.size(); i++) {
                    if (DownloadDataBase.glosne_przeklinanie.get(i).getDistrict().equals(description)) {
                        PRZEKLINANIE++;
                    }
                }

                for (int i = 0; i < DownloadDataBase.porwanie.size(); i++) {
                    if (DownloadDataBase.porwanie.get(i).getDistrict().equals(description)) {
                        PORAWANIE++;
                    }
                }
                for (int i = 0; i < DownloadDataBase.zabojstwo.size(); i++) {
                    if (DownloadDataBase.zabojstwo.get(i).getDistrict().equals(description)) {
                        ZABOJSTWO++;
                    }
                }
                for (int i = 0; i < DownloadDataBase.przemoc_domowa.size(); i++) {
                    if (DownloadDataBase.przemoc_domowa.get(i).getDistrict().equals(description)) {
                        PRZEMOC_DOMOWA++;
                    }
                }


                android.support.v7.app.AlertDialog.Builder adb = new android.support.v7.app.AlertDialog.Builder(
                        map.this);
                adb.setTitle(description);
                adb.setMessage("Zanotowano " + counter + " wykroczeń"
                        + "\nPrzeklinanie: " + PRZEKLINANIE
                        + "\n Porwania: " + PORAWANIE
                        + "\n Zabójstwa: " + ZABOJSTWO
                        + "\n Przemoc domowa: "
                        + PRZEMOC_DOMOWA);
                adb.setPositiveButton("Ok", null);
                adb.show();


            }

        });
        //googleMap.setOnMapClickListener(this);


    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.B_clear: {
                mMap.clear();
                customOffenses.clear();
                isPressedB_zabojstwo = false;
                isPressedB_porwania = false;
                isPressedB_przeklenstwa = false;
                isPressedB_przemoc = false;
                isMapColor = false;
                break;
            }

            case R.id.B_morderstwa: {
                if (isPressedB_zabojstwo) {
                    isPressedB_zabojstwo = false;
                    //bMorderstwa.setBackgroundColor(0xF51B5);
                } else {
                    isPressedB_zabojstwo = true;

                }
                break;
            }

            case R.id.B_porwania: {
                if (isPressedB_porwania) {
                    isPressedB_porwania = false;
                } else {
                    isPressedB_porwania = true;
                }
                break;
            }
            case R.id.B_przeklinanie: {
                if (isPressedB_przeklenstwa) {
                    isPressedB_przeklenstwa = false;
                } else {
                    isPressedB_przeklenstwa = true;
                }

                    /*for (offense sthHappened: DownloadDataBase.glosne_przeklinanie) {
                    customOffenses.add(sthHappened);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(sthHappened.getPosition_longitude(),sthHappened.getPosition_latitude() ))
                            .title(sthHappened.getDescription()));
                }*/
                break;
            }
            case R.id.B_przemoc: {
                if (isPressedB_przemoc) {
                    isPressedB_przemoc = false;
                } else {
                    isPressedB_przemoc = true;
                }
                break;
            }
            case R.id.B_showD: {
                if (isMapColor) {
                    mMap.clear();
                    isMapColor = false;
                } else {

                    isMapColor = true;
                }
                break;
            }
            case R.id.B_zadzwon: {
                if (tempChosenCap<100000) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+DownloadDataBase.policemanList.get(tempChosenCap).phoneNumber));
                    startActivity(callIntent);
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                } else {

                }
                break;
            }
        }


        customOffenses.clear();
        mMap.clear();
        if(isPressedB_zabojstwo)
        {
            addEvent(DownloadDataBase.zabojstwo);
        }
        if(isPressedB_porwania)
        {
            addEvent(DownloadDataBase.porwanie);
        }
        if(isPressedB_przeklenstwa)
        {
            addEvent(DownloadDataBase.glosne_przeklinanie);
        }
        if(isPressedB_przemoc)
        {
            addEvent(DownloadDataBase.przemoc_domowa);
        }

        drawEverything();

    }

    private String  getMapsApiDirectionsUrl(LatLng origin,LatLng dest) {
        // Origin of route
        //String originStreet = getCompleteAddressString(origin.latitude, origin.longitude);
        String str_origin = "origin="+origin.longitude+","+origin.latitude;

        // Destination of route
        //String destinationStreet=getCompleteAddressString(dest.latitude, dest.longitude);
        String str_dest = "destination="+dest.longitude+","+dest.latitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }


    private class ReadTask extends AsyncTask<String, Void , String> {

        @Override
        protected String doInBackground(String... url) {
            // TODO Auto-generated method stub
            String data = "";
            try {
                MapHttpConnection http = new MapHttpConnection();
                data = http.readUr(url[0]);


            } catch (Exception e) {
                // TODO: handle exception
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }

    }

    private void drawEverything() {

        for(int i=0; i<policeman.size();i++) {
            mMap.addMarker(policeman.get(i));
        }
        for (offense sthHappened : customOffenses) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(sthHappened.getPosition_longitude(), sthHappened.getPosition_latitude()))
                    .title(sthHappened.getTypeConverted()+" nr: "+sthHappened.getOffenseId()).snippet(sthHappened.toString()));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker marker) {
                    //mMap.clear();
                    counter=0;
                    shortestRoute=100000000000000000d;
                    polylinesToAdd.clear();

                    for(int i=0; i<policeman.size();i++)
                    {
                        if(marker.getPosition().equals(policeman.get(i).getPosition()))
                        {
                            tempChosenCap = i;
                            break;
                        }
                        else {
                            String url = getMapsApiDirectionsUrl(
                                    new LatLng(marker.getPosition().longitude,
                                            marker.getPosition().latitude),
                                    new LatLng((policeman.get(i).getPosition().longitude),
                                            (policeman.get(i).getPosition().latitude)));
                            ReadTask downloadTask = new ReadTask();
                            // Start downloading json data from Google Directions API
                            downloadTask.execute(url);
                        }
                    }
                    return false;
                }


            });

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    LinearLayout info = new LinearLayout(getApplicationContext());
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(getApplicationContext());
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(getApplicationContext());
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);


                    return info;
                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    marker.hideInfoWindow();
                    tempChosenCap = 100000;

                }
            });


        }



        if(isMapColor)
            colorMap();
    }

    private void addEvent(List<offense> accidentList)
    {
        for (offense sthHappened: accidentList) {
            customOffenses.add(sthHappened);
        }
    }


    private void showEvent(List<offense> accidentList)
    {
        for (offense sthHappened: accidentList) {
            customOffenses.add(sthHappened);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(sthHappened.getPosition_longitude(),sthHappened.getPosition_latitude() ))
                    .title(sthHappened.getTypeConverted()).snippet(sthHappened.toString()));
        }
    }

    private void colorMap()
    {

        final int COLOR_GREEN = 0x80009900;
        final int COLOR_YELLOW = 0x80FFFF00;
        final int COLOR_ORANGE = 0x80FFbf00;
        final int COLOR_RED = 0x80CC0000;
        int color = 0;
        String dName=null;
        int dCounter;

        for(int i=0; i<DownloadDataBase.districts.size(); i++) {
            PolygonOptions opts = new PolygonOptions();
            //Polygon polygon = mMap.addPolygon(opts);

            for (LatLng location : DownloadDataBase.districts.get(i).getList())
            {
                opts.add(location);
            }

            dName = DownloadDataBase.districts.get(i).getDistrictName();
            dCounter = 0;
                        /*for (int j = 0; j < DownloadDataBase.offenses.size(); j++) {
                            if (DownloadDataBase.offenses.get(j).getDistrict().equals(dName)) {
                                dCounter++;
                            }

                        }*/
            for (offense chosenOffense: customOffenses
                    ) {
                if (chosenOffense.getDistrict().equals(dName)) {
                    dCounter++;
                }
            }

            if(dCounter==0){
                color = COLOR_GREEN;
            }else if(dCounter>0 && dCounter<=2){
                color = COLOR_YELLOW;
            }else if(dCounter>2 && dCounter<=4){
                color = COLOR_ORANGE;
            }else if(dCounter>=4){
                color = COLOR_RED;
            }

            Polygon polygon = mMap.addPolygon(opts
                    .strokeColor(Color.RED)
                    .fillColor(color)     //przezroczystosc trza ustawic!
                    .strokeWidth(1)
            );
            DownloadDataBase.districts.get(i).addPolygon(polygon);
            polygon.setClickable(true);
            polygonList.add(polygon);
        }

    }

        /*////////////////////////do usuniecia
        LatLng gdansk = new LatLng(54.22,18.38);

        mMap.addMarker(new MarkerOptions().position(gdansk).title("Marker")); // dodanie nowego markera
        // tutaj tak samo mozna dodawać listenery do mapy
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gdansk));
        ////////////////////////az dotad.**/


    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if(curreLocationMarker!=null)
        {
            curreLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        curreLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(5));

        if(client!=null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected float calculateMiles(List<LatLng> points) {
        float totalDistance = 0;

        for(int i = 1; i < points.size(); i++) {
            Location currLocation = new Location("this");
            currLocation.setLatitude(points.get(i).latitude);
            currLocation.setLongitude(points.get(i).longitude);

            Location lastLocation = new Location("this");
            currLocation.setLatitude(points.get(i-1).latitude);
            currLocation.setLongitude(points.get(i-1).longitude);

            totalDistance += lastLocation.distanceTo(currLocation);
        }
        return totalDistance;
    }


    private class ParserTask extends AsyncTask<String,Integer, List<List<HashMap<String , String >>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            // TODO Auto-generated method stub
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {


                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }


                polyLineOptions.addAll(points);
                polyLineOptions.width(4);
                polyLineOptions.color(Color.BLUE);

                double distance = calculateMiles(points);

                //int minorCounter=0;
                if(distance<shortestRoute)
                {
                    shortestRoute=distance;
                    tempCounter=counter;
                }

                polylinesToAdd.add(polyLineOptions);
                //mMap.addPolyline(polyLineOptions);
                if(polylinesToAdd.size()==policeman.size())
                {
                    for (int g = 0; g < polylinesToAdd.size(); g++) {
                        if(g==tempCounter)
                        {
                            mMap.addPolyline(polylinesToAdd.get(g).width(8).color(Color.RED));
                        }else
                        mMap.addPolyline(polylinesToAdd.get(g));
                    }
                    counter=0;
                    tempCounter=0;
                }
                counter++;
            }
        }
    }
}
