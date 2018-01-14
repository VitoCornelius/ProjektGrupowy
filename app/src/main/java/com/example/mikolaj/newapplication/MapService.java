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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This shows how to create a simple activity with a MapService and a marker on the MapService.
 */
public class MapService extends AppCompatActivity implements OnMapReadyCallback {

    public static String TAG = "mapka";

    private TextView gpsTextView;
    private Button locationButton;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private GoogleMap googleMap;


    private LatLng myposition;
    private Marker myLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map); // ustawienie widoku na jakiś taki z xml

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); // pobranie tej klasy w widoku
        mapFragment.getMapAsync(this); // pobranie async tej mapy

        Log.i(TAG, "stworzenie mapy");
        initializeGps();

    }

    /**
     * Ustawienie GPS
     */
    private void initializeGps() {

        gpsTextView = (TextView) findViewById(R.id.coordinate_gps);
        locationButton = (Button) findViewById(R.id.button_gps);
        // pobranie androidowego
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        configureGpsListener();
    }

    /**
     *
     */
    private void configureGpsListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // dodanie napisu jaka jest lokalizacja aktualna
                gpsTextView.append("\n " + location.getLongitude() + " " + location.getLatitude());
                //ustawienie pizdlika na mapie
                setPizdlik(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                // jeżeli nie jest włączony GPS to jest przekierowanie do ustawień androida
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
    }

    /**
     * ustawienie wlasnego polozenie na mapie
     */
    private void setPizdlik(Location location) {

        myposition = new LatLng(location.getLatitude(), location.getLongitude());
        myLocationMarker = googleMap.addMarker(new MarkerOptions().position(myposition).title("Moja lokalizacja"));
        myLocationMarker.setTag(0);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;

        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO tutaj jest blad ale jeszcze się zastanowie jak to ogarnać - btw dziala z tym errorem
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        });
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker")); // dodanie nowego markera
        // tutaj tak samo mozna dodawać listenery do mapy
    }
}
