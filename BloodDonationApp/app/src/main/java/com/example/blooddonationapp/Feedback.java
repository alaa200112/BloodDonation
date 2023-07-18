package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.blooddonationapp.Models.postModel;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Button feed = (Button) findViewById(R.id.submit);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feed.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"feedback empty",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Thank you for this feedback",Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}