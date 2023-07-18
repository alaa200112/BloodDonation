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
import com.example.blooddonationapp.Models.userModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView postTextBtn, searchBtn, nearestTextBtn,findBloodDonor;
    private ViewPager2 viewPager2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        postTextBtn = (TextView) findViewById(R.id.idPosts);
        searchBtn = (TextView) findViewById(R.id.idSearchGPS);
        nearestTextBtn = (TextView) findViewById(R.id.nearby_hospital);
        findBloodDonor = (TextView) findViewById(R.id.idfindBloodDonor);

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

        userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"),getIntent().getExtras().getString("image"));
        findBloodDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,showDonars.class);
                intent.putExtra("userEmail",user.getEmail());
                startActivity(intent);

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,testmapUser.class);
                startActivity(intent);

            }
        });
postTextBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(HomeActivity.this,showPosts.class);
        intent.putExtra("userEmail", user.getEmail());
        intent.putExtra("pass", user.getPass());
        intent.putExtra("gender", user.getGender());
        intent.putExtra("bloodType", user.getBloodType());
        intent.putExtra("name", user.getName());
        intent.putExtra("phone", user.getPhone());
        intent.putExtra("address", user.getAddress());
        intent.putExtra("image", user.getAddress());
        intent.putExtra("available", user.getAvailable());
        intent.putExtra("last_donated", user.getLast_donated());
        intent.putExtra("total_donated", user.getTotal_donated());
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
                        Toast.makeText(HomeActivity.this, "Home is Clicked", Toast.LENGTH_SHORT).show();break;

                    case R.id.nav_feedback:
                        startActivity(new Intent(HomeActivity.this,Feedback.class));break;
                    case R.id.nav_archievement:
                        Intent intent22=new Intent(HomeActivity.this,achieve.class);
                        intent22.putExtra("userEmail", user.getEmail());
                        intent22.putExtra("pass", user.getPass());
                        intent22.putExtra("gender", user.getGender());
                        intent22.putExtra("bloodType", user.getBloodType());
                        intent22.putExtra("name", user.getName());
                        intent22.putExtra("phone", user.getPhone());
                        intent22.putExtra("available", user.getAvailable());
                        intent22.putExtra("address", user.getAddress());
                        intent22.putExtra("image", user.getAddress());
                        intent22.putExtra("last_donated", user.getLast_donated());
                        intent22.putExtra("total_donated", user.getTotal_donated());
                        startActivity(intent22);
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(HomeActivity.this,Login.class));break;
                    case R.id.nav_aboutUs:
                        startActivity(new Intent(HomeActivity.this,AboutAus.class));break;
                    case R.id.nav_bloodInfo:
                        startActivity(new Intent(HomeActivity.this,BloodInfo.class));break;
                    case R.id.nav_profile:
                    Intent intent=new Intent(HomeActivity.this,SettingsActivity.class);
                    intent.putExtra("userEmail", user.getEmail());
                    intent.putExtra("pass", user.getPass());
                    intent.putExtra("gender", user.getGender());
                    intent.putExtra("bloodType", user.getBloodType());
                    intent.putExtra("name", user.getName());
                    intent.putExtra("phone", user.getPhone());
                    intent.putExtra("address", user.getAddress());
                    intent.putExtra("image", user.getAddress());
                        startActivity(intent);
                        break;
                    case R.id.nav_donar:
                        Intent i=new Intent(HomeActivity.this,showDonars.class);
                        i.putExtra("userEmail", user.getEmail());
                        i.putExtra("pass", user.getPass());
                        i.putExtra("gender", user.getGender());
                        i.putExtra("bloodType", user.getBloodType());
                        i.putExtra("name", user.getName());
                        i.putExtra("phone", user.getPhone());
                        i.putExtra("address", user.getAddress());
                        i.putExtra("image", user.getImage());
                        i.putExtra("available", user.getAvailable());
                        i.putExtra("last_donated", user.getLast_donated());
                        i.putExtra("total_donated", user.getTotal_donated());
                        startActivity(i);
                        break;
                    case R.id.rr:
                        Intent intent4=new Intent(HomeActivity.this, showRequest.class);
                        intent4.putExtra("userEmail", user.getEmail());
                        intent4.putExtra("pass", user.getPass());
                        intent4.putExtra("gender", user.getGender());
                        intent4.putExtra("bloodType", user.getBloodType());
                        intent4.putExtra("name", user.getName());
                        intent4.putExtra("phone", user.getPhone());
                        intent4.putExtra("address", user.getAddress());
                        intent4.putExtra("image", user.getAddress());
                        intent4.putExtra("available", user.getAvailable());
                        intent4.putExtra("last_donated", user.getLast_donated());
                        intent4.putExtra("total_donated", user.getTotal_donated());
                        startActivity(intent4);
                        break;
                    case R.id.mmm:
                        Intent intent5=new Intent(HomeActivity.this,show_message.class);
                        intent5.putExtra("userEmail", user.getEmail());
                        intent5.putExtra("pass", user.getPass());
                        intent5.putExtra("gender", user.getGender());
                        intent5.putExtra("bloodType", user.getBloodType());
                        intent5.putExtra("name", user.getName());
                        intent5.putExtra("phone", user.getPhone());
                        intent5.putExtra("address", user.getAddress());
                        intent5.putExtra("image", user.getAddress());
                        intent5.putExtra("available", user.getAvailable());
                        intent5.putExtra("last_donated", user.getLast_donated());
                        intent5.putExtra("total_donated", user.getTotal_donated());
                        startActivity(intent5);
                        break;


                    default:
                        return true;

                }
                return true;
            }
        });
    }
    }
