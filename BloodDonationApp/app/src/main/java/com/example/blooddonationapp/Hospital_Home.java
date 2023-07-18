package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.blooddonationapp.Adapter.SliderAdapter;
import com.example.blooddonationapp.Models.SliderItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Hospital_Home extends AppCompatActivity {
    private TextView postTextBtn, searchBtn, nearestTextBtn,findBloodDonor;
    private ViewPager2 viewPager2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_home);
        postTextBtn = (TextView) findViewById(R.id.Campaigns);
        searchBtn = (TextView) findViewById(R.id.searchGPS);
        nearestTextBtn = (TextView) findViewById(R.id.nearest);
        findBloodDonor = (TextView) findViewById(R.id.findBloodDonor);
        viewPager2 =findViewById(R.id.viewpager2);
        List<SliderItem> sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.a));
        sliderItems.add(new SliderItem(R.drawable.o));
        sliderItems.add(new SliderItem(R.drawable.c));
        sliderItems.add(new SliderItem(R.drawable.d));
        sliderItems.add(new SliderItem(R.drawable.e));
        sliderItems.add(new SliderItem(R.drawable.f));
        sliderItems.add(new SliderItem(R.drawable.g));
        sliderItems.add(new SliderItem(R.drawable.j));
        sliderItems.add(new SliderItem(R.drawable.i));
        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        String name=getIntent().getExtras().getString("name");
        String email=getIntent().getExtras().getString("userEmail");
       findBloodDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hospital_Home.this,showDonars.class);
                intent.putExtra("userEmail",email);
                startActivity(intent);

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hospital_Home.this,testmap.class);
                startActivity(intent);

            }
        });
        postTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Hospital_Home.this,cam.class);
                intent.putExtra("userEmail",email);
                intent.putExtra("name", name);
                startActivity(intent);

            }
        });



        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.nav_home:
                        Toast.makeText(Hospital_Home.this, "Home is Clicked", Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_logout:
                        startActivity(new Intent(Hospital_Home.this,Login.class));break;
                    case R.id.nav_aboutUs:
                        startActivity(new Intent(Hospital_Home.this,AboutAus.class));break;
                    case R.id.nav_bloodInfo:
                        startActivity(new Intent(Hospital_Home.this,BloodInfo.class));break;
                    case R.id.nav_chart:
                        startActivity(new Intent(Hospital_Home.this,histogramDonars.class));break;
                    case R.id.requestHospital:
                        Intent intent4=new Intent(Hospital_Home.this, RequestHospitalActivity.class);
                        intent4.putExtra("hospitalEmail",email);
                        startActivity(intent4);
                        break;
                    case R.id.nav_profile:
                        Intent intent=new Intent(Hospital_Home.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_donar:
                        Intent intent1=new Intent(Hospital_Home.this,showDonars.class);
                        startActivity(intent1);break;
                    default:
                        return true;

                }
                return true;
            }
        });

    }
}