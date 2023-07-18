package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;

import com.example.blooddonationapp.Models.Donor;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.blooddonationapp.databinding.ActivityTestmapBinding;

import java.util.ArrayList;
import java.util.List;

public class testmapUser extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTestmapBinding binding;
    List<Donor> donors = new ArrayList<>();
    Donor nearstdonor;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        donors.add(new Donor("Al-Mounira", 30.0457, 31.2456,"0101772522"));
        donors.add(new Donor("El Kasr El Aini", 30.0425, 31.2249,"0101152525"));
        donors.add(new Donor("Al Hawd Al Marsoud ", 30.0208, 31.2246,"01561827262"));
        donors.add(new Donor("New October", 29.9712,30.9625,"0115666211"));
        binding = ActivityTestmapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        client= LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if(!isPermissionGranted())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1002);
            }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Update the UI with the nearest donor
                Donor nearestDonor = getNearestDonor(location.getLatitude(), location.getLongitude(), donors);
                // TODO: Update the UI with the nearest donor's information

                // Show the location of donors on the map
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
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(isPermissionGranted())
        {
            makehomelocation();
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // showDonorLocationsOnMap(donors);


    }
    private Donor getNearestDonor(double latitude, double longitude, List<Donor> donors) {
        Donor nearestDonor = null;
        double smallestDistance = Double.MAX_VALUE;

        for (Donor donor : donors) {
            double distance = distance(latitude, longitude, donor.getLatitude(), donor.getLongitude());

            if (distance < smallestDistance) {
                nearestDonor = donor;
                smallestDistance = distance;
            }
        }

        return nearestDonor;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515; // Convert to miles
        dist = dist * 1.609344; // Convert to kilometers
        return dist;
    }
    private void showDonorLocationsOnMap(List<Donor> donors) {
        // Create a GoogleMap object if it hasn't already been created

        // Clear any existing markers on the map

        // Add a marker for each donor on the map
        for (Donor donor : donors) {
            LatLng latLng = new LatLng(donor.getLatitude(), donor.getLongitude());
            if(!donor.equals(nearstdonor)) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(donor.getName()+" Phone: "+donor.getPhone());
                mMap.addMarker(markerOptions);
            }
            else
            {
/**
 MarkerOptions markerOptions = new MarkerOptions()
 .position(latLng)
 .title(donor.getName()+" Phone: "+donor.getPhone());
 mMap.addMarker(markerOptions);
 **/
                BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(donor.getName() + " Phone: " + donor.getPhone())
                        .icon(markerIcon);
                mMap.addMarker(markerOptions);

            }
        }
    }
    private boolean isPermissionGranted(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            makehomelocation();
    }
    @SuppressLint("MissingPermission")
    private void makehomelocation() {
        if(mMap!=null){
            mMap.setMyLocationEnabled(true);
            LocationCallback locationCallback=new LocationCallback() {

                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    nearstdonor=getNearestDonor(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(),donors);
                    LatLng home=new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                    client.removeLocationUpdates(this);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home,15f));
                    showDonorLocationsOnMap(donors);

                }
            };
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval( 5000);
            client.requestLocationUpdates(locationRequest, locationCallback,
                    Looper.getMainLooper());
        }
    }
}