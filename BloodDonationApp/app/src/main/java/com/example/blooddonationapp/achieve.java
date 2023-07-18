package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class achieve extends AppCompatActivity {
    FirebaseFirestore reference;
    TextView emailText,phoneText,addressText;
    String name,phone,pass,address,gender,bloodType,email,available,total_donated,image;
    Date c1 = Calendar.getInstance().getTime();
    SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate22 = df1.format(c1);
    static  int x=1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);
        emailText=findViewById(R.id.Email);
        phoneText=findViewById(R.id.phone);
       addressText=findViewById(R.id.map);
        TextView last_don=(TextView) findViewById(R.id.LastDonate);
        TextView total_don=(TextView) findViewById(R.id.settotalDonate);
        TextView next_don=(TextView) findViewById(R.id.setnextDonate);
        userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"));
        emailText.setText(user.getEmail());
        phoneText.setText(user.getPhone());
        addressText.setText(user.getAddress());
        reference = FirebaseFirestore.getInstance();
        reference.collection("Users")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        userModel user;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String last= documentSnapshot.getString("last_donated");
                            String total= documentSnapshot.getString("total_donated");
                            last_don.setText(last);
                            total_don.setText(total);
                            SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

                            Calendar cal = Calendar.getInstance();
                            try{
                                cal.setTime(df1.parse(last));
                            }catch(ParseException e){
                                e.printStackTrace();
                            }

                            // use add() method to add the days to the given date
                            cal.add(Calendar.DAY_OF_MONTH, 90);
                            String formattedDate = df1.format(cal.getTime());
                            next_don.setText(formattedDate);
                            //System.out.println(formattedDate);

                            Toast.makeText(getApplicationContext()," Your Achievement ",Toast.LENGTH_LONG).show();

                        }
                    }
                });
        if(formattedDate22.equals(next_don.getText())||formattedDate22.compareTo(String.valueOf(next_don.getText()))>0)
        {
            next_don.setText("0");
            updateus();
        }
    }
    private void updateus()
    {
        userModel user2=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"));

        String userId="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name",user2.getName());
        userMap. put("address",user2.getAddress() );
        userMap. put("phone",user2.getPhone() );
        userMap. put("bloodType",user2.getBloodType());
        userMap. put("gender", user2.getGender());
        userMap. put("email",user2.getEmail());
        userMap. put("pass",user2.getPass());
        userMap. put("image",user2.getImage());
        userMap. put("last_donated",user2.getLast_donated());
        userMap. put("available","true");
        userMap.put("total_donated",user2.getTotal_donated());

        // userMap. put("pass", pass);
        db.collection("Users")
                .document(userId)
                .set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "DocumentSnapshot successfully written.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"error.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}