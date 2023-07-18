package com.example.blooddonationapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class myLocationListen implements LocationListener {
    private Context activity;

    public myLocationListen(Context activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(activity,"GPS Enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(activity,"GPS Disabled",Toast.LENGTH_LONG).show();
    }
}
