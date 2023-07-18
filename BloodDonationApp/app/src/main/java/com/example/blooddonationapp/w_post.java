package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonationapp.Models.postModel;
import com.example.blooddonationapp.Models.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class w_post extends AppCompatActivity {
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
static  int x=1;
    EditText des,title;
    Button pos;
    TextView undoTextBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wpost);
        userModel user2=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"));


       // getSupportActionBar().setTitle("Write Post");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         des=(EditText) findViewById(R.id.getdes);
         pos=(Button) findViewById(R.id.postingbtn);
        userModel user1=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"));

        String userId="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        String finalUserId = userId;
        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d=des.getText().toString();

                if(des.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"title OR description empty",Toast.LENGTH_LONG).show();
                }
                else{

                    postModel p=new postModel();
                    p.saveData(finalUserId+"_"+x,d,formattedDate,user1.getEmail(),"Post",user1.getName());
                    Toast.makeText(getApplicationContext(), "post save Successfully", Toast.LENGTH_SHORT).show();
                    x++;
                    //#UNDO TO Home PAGE///////////
                    Intent intent=new Intent(w_post.this,showPosts.class);
                    intent.putExtra("userEmail", user2.getEmail());
                    intent.putExtra("pass", user2.getPass());
                    intent.putExtra("gender", user2.getGender());
                    intent.putExtra("bloodType", user2.getBloodType());
                    intent.putExtra("name", user2.getName());
                    intent.putExtra("phone", user2.getPhone());
                    intent.putExtra("address", user2.getAddress());
                    intent.putExtra("image", user2.getAddress());
                    startActivity(intent);
                    finish();
                }

            }
        });

//mDatabase = FirebaseDatabase.getInstance().getReference();
//                mDatabase.child("posts").setValue(p);
    }
}