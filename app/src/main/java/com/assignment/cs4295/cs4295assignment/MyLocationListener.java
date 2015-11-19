package com.assignment.cs4295.cs4295assignment;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by dickson on 11/19/15.
 */
public class MyLocationListener implements LocationListener
{
    public static double latitude;
    public static double longitude;

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
