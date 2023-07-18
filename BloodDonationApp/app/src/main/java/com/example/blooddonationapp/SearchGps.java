package com.example.blooddonationapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Models.Donor;
import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SearchGps extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    myLocationListen locationListen;
    LocationManager locationManager;
    EditText editText;
    private DatabaseReference mDatabase;
    FirebaseFirestore reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gps);

        List<Donor> donors = new ArrayList<>();
        donors.add(new Donor("John", 30.0453, 31.2242,"0110145226"));
        donors.add(new Donor("Jane", 30.1167, 31.4000,"01262626262"));
        donors.add(new Donor("Bob", 29.9792, 31.1342,"0101727227"));
        donors.add(new Donor("Alice", 30.0471,31.2623,"0161116161"));
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Update the UI with the nearest donor
                Donor nearestDonor = getNearestDonor(location.getLatitude(), location.getLongitude(), donors);
                // TODO: Update the UI with the nearest donor's information
                showDonorLocationsOnMap(donors);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"));
        map = googleMap;
       System.out.println("ana hennnnna");
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 8));
        /*reference = FirebaseFirestore.getInstance();
        reference.collection("Users")
                .whereEqualTo("email", user.getEmail())
                .get()*/


        map.clear();
        Location loc = null;

        try {
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "You did not allow to access", Toast.LENGTH_LONG).show();
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        map.setMyLocationEnabled(true);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)
                {
                    String search = editText.getText().toString();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    List<Address> addressList = new ArrayList<Address>();

                    try {
                        addressList = geocoder.getFromLocationName(search,1);
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_LONG).show();
                    }
                    Address address1 = addressList.get(0);

                    Address address = addressList.get(0);
                    Toast.makeText(getApplicationContext(), "Found Address : " + address.toString(), Toast.LENGTH_LONG).show();
                    LatLng latLng = new LatLng(address.getLatitude(),address1.getLongitude());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                    MarkerOptions options = new MarkerOptions().position(latLng);
                    map.addMarker(options);
                }
                return false;
            }
        });
        //showDonorLocationsOnMap(donors);
    }
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("location");
    ValueEventListener usersListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                String userId = userSnapshot.getKey();
                LatLng location = userSnapshot.child("location").getValue(LatLng.class);
                map.addMarker(new MarkerOptions().position(location).title(userId));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "loadUsers:onCancelled", databaseError.toException());
        }
    };

    public void savelocation(Location location) {
        mDatabase.setValue(location);
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
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(donor.getName());
            Marker marker=map.addMarker(markerOptions);
            CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(marker.getPosition(),15);
            map.animateCamera(cameraUpdate);

        }

    }

}