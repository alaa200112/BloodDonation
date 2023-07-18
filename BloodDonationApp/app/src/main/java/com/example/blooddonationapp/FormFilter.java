package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.blooddonationapp.Models.requestModel;
import com.example.blooddonationapp.Models.userModel;

public class FormFilter extends AppCompatActivity {
    boolean num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_filter);
         String date=getIntent().getExtras().getString("formattedDate");
        String creator_email=getIntent().getExtras().getString("creator_email");
        String recive=getIntent().getExtras().getString("recive");
        String available=getIntent().getExtras().getString("available");

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Drawable drawable = getResources().getDrawable(R.drawable.image);
        image.setImageDrawable(drawable);

        num = true;

        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup_flu);
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup_dentist);
        RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup_tattoo);
        RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup_vaccine);
        RadioGroup radioGroup5 = (RadioGroup) findViewById(R.id.radioGroup_asthma);
        RadioGroup radioGroup6 = (RadioGroup) findViewById(R.id.radioGroup_cancer);
        RadioGroup radioGroup7 = (RadioGroup) findViewById(R.id.radioGroup_drugs);
        RadioGroup radioGroup9 = (RadioGroup) findViewById(R.id.radioGroup_aids);


        Button button = (Button) findViewById(R.id.predict);

        EditText editText = (EditText) findViewById(R.id.editTextNumber);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioId1 = radioGroup1.getCheckedRadioButtonId();
                int radioId2 = radioGroup2.getCheckedRadioButtonId();
                int radioId3 = radioGroup3.getCheckedRadioButtonId();
                int radioId4 = radioGroup4.getCheckedRadioButtonId();
                int radioId5 = radioGroup5.getCheckedRadioButtonId();
                int radioId6 = radioGroup6.getCheckedRadioButtonId();
                int radioId7 = radioGroup7.getCheckedRadioButtonId();
                int radioId9 = radioGroup9.getCheckedRadioButtonId();

                if(radioId1 == R.id.yes_flu || radioId2 == R.id.yes_dentist ||
                        radioId3 == R.id.yes_tattoo || radioId4 == R.id.yes_vaccine ||
                        radioId5 == R.id.yes_asthma || radioId6 == R.id.yes_cancer ||
                        radioId7 == R.id.yes_drugs  || radioId9 == R.id.yes_aids)
                {num = false;}
                else
                {num = true;}

                userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"),getIntent().getExtras().getString("image"));

                if(num)
                {

                        requestModel rm= new requestModel();
                        rm.saveData("I want to make donation ",date,creator_email,"Request Donation",recive,"false");
                        Toast.makeText(getApplicationContext(),"You Can make donation",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(FormFilter.this,showPosts.class);
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
                        finish();



                }
                else
                {

                    Toast.makeText(getApplicationContext(),"You Can not make donation",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}