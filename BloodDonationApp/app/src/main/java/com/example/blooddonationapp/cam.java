package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Models.postModel;
import com.example.blooddonationapp.Models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class cam extends AppCompatActivity {

    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    ImageView m;
    EditText Stime,Etime,Location,des;
    static  int x=1;
    private DatabaseReference mDatabase;
    Button pos;
    ImageButton undoTextBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
       /* final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userModel user1=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"));
        m=(ImageView)findViewById(R.id.imageView3);
        undoTextBtn = (ImageButton) findViewById(R.id.undo);
        undoTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cam.this,Hospital_Home.class);
                intent.putExtra("userEmail", user1.getEmail());
                intent.putExtra("pass", user1.getPass());
                intent.putExtra("gender", user1.getGender());
                intent.putExtra("bloodType", user1.getBloodType());
                intent.putExtra("name", user1.getName());
                intent.putExtra("phone", user1.getPhone());
                intent.putExtra("address", user1.getAddress());
                intent.putExtra("image", user1.getAddress());
                startActivity(intent);
                finish();
            }
        });
        Stime=(EditText) findViewById(R.id.Stime);
        Etime=(EditText) findViewById(R.id.Etime);
        Location=(EditText) findViewById(R.id.getLocation);
        des=(EditText) findViewById(R.id.post);
        pos=(Button) findViewById(R.id.postingbtn);
        String userId="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        String finalUserId = userId;
        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=getIntent().getExtras().getString("name");
                String email=getIntent().getExtras().getString("userEmail");

                String d=des.getText().toString()+" from "+Stime.getText().toString()+"to "+Etime.getText().toString()+"\n in Address : "+Location.getText().toString();

                if(Stime.equals("")&&des.equals("")&&Etime.equals("")&&Location.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"title OR description empty",Toast.LENGTH_LONG).show();
                }
                else{

                    postModel p=new postModel();
                    p.saveData(finalUserId+"_"+x,d,formattedDate,email,"Blood donation Campaigns",name);
                    Toast.makeText(getApplicationContext(), "post save Successfully", Toast.LENGTH_SHORT).show();
                    x++;
                }

            }
        });



    }
}
