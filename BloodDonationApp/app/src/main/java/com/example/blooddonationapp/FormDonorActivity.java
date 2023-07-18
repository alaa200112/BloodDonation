package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonationapp.Models.requestModel;
import com.example.blooddonationapp.Models.userModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormDonorActivity extends AppCompatActivity {
  EditText Recency,Frequency,Monetary,Time;
  TextView result;
  Button predict;
  String url= "http://192.168.1.7:8080/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_donor);
        String date=getIntent().getExtras().getString("formattedDate");
        String creator_email=getIntent().getExtras().getString("creator_email");
        String recive=getIntent().getExtras().getString("recive");
        Recency=findViewById(R.id.Recency);
        Frequency=findViewById(R.id.Frequency);
        Monetary=findViewById(R.id.Monetary);
        Time=findViewById(R.id.Time);
        predict=(Button) findViewById(R.id.predict);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String data=jsonObject.getString("makeDonate");
                            if(data.equals("1")) {
                                userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"),getIntent().getExtras().getString("image"));
                                requestModel rm= new requestModel();
                                rm.saveData("I want to make donation ",date,creator_email,"Request Donation",recive,"false");
                                Toast.makeText(FormDonorActivity.this, "You can donate so request is done ,thank you", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(FormDonorActivity.this,showPosts.class);
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
                                Toast.makeText(FormDonorActivity.this,"Sorry, you cannot make donate ", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormDonorActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                   protected Map<String,String> getParams(){
                      Map<String,String> params=new HashMap<String,String>();
                      params.put("Recency (months)",Recency.getText().toString());
                      params.put("Frequency (times)",Frequency.getText().toString());
                      params.put("Monetary (c.c. blood)",Monetary.getText().toString());
                      params.put("Time (months)",Time.getText().toString());
                      return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(FormDonorActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}