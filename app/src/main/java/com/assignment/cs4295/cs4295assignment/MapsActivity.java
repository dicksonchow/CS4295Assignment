package com.assignment.cs4295.cs4295assignment;

import android.content.Context;;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final float SPEED_OF_PLANE = 1020 * 1000;

    // Variables that refers to the items shown
    private SupportMapFragment mapFragment;
    private EditText disToDest;
    private EditText timeTaken;

    // Variables that are used in the activities
    private double my_latitude;
    private double my_longitude;
    private double des_latitude;
    private double des_longitude;
    private boolean showPolyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        showPolyLine = false;
        // To be modified
        // suppose get parameters from the other classes/activities
        Intent intent = getIntent();
        des_latitude = intent.getDoubleExtra("latitude", 0);
        des_longitude = intent.getDoubleExtra("longitude", 0);

        disToDest = (EditText) findViewById(R.id.disToDest);
        timeTaken = (EditText) findViewById(R.id.timeTaken);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void buttonShowDesIsClicked(View view){
        // Hard-coded location of Mong Kok Stadium
        float[] result = new float[1];
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener lls = new MyLocationListener();
        DecimalFormat df = new DecimalFormat("#.##");

        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lls);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, lls);

            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Location.distanceBetween(22.3371078, 114.170141,
                my_latitude = MyLocationListener.latitude;
                my_longitude = MyLocationListener.longitude;
                Location.distanceBetween(my_latitude, my_longitude,
                        des_latitude, des_longitude, result);
                disToDest.setText(String.format("%s meter", df.format(result[0])));
                timeTaken.setText(String.format("%.2f minutes", result[0] / SPEED_OF_PLANE * 60));

                showPolyLine = true;
                mapFragment.getMapAsync(this);
            }
            else{
                disToDest.setText("GPS is not turned on.");
            }
        }
        catch (SecurityException e){
            disToDest.setText("Go to your setting.");
        }
        catch (Exception e){
            disToDest.setText("Something went wrong.");
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.setMyLocationEnabled(true);

        if (showPolyLine){
            map.addPolyline(new PolylineOptions()
                    .add(new LatLng(my_latitude, my_longitude),
                            new LatLng(des_latitude, des_longitude))
                    .width(5)
                    .color(Color.RED)
            );
        }
        else {
            LatLng myLoc = new LatLng(des_latitude, des_longitude);
            map.addMarker(new MarkerOptions().position(myLoc));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 15));
        }
    }


}
