package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.blooddonationapp.Adapter.messageAdapter;
import com.example.blooddonationapp.Models.messageModel;
import com.example.blooddonationapp.Models.requestModel;

import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class show_message extends AppCompatActivity {
    private RecyclerView recyclerView3;
    List<messageModel> message;
    FirebaseFirestore reference;
    String reciver_email;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    messageAdapter messageListAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_message);
        recyclerView3 = findViewById(R.id.recyclerView3);
        reciver_email=getIntent().getExtras().getString("userEmail");
        userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"));
        ImageButton undoTextBtn = (ImageButton) findViewById(R.id.undo);
        undoTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(show_message.this,HomeActivity.class);
                intent.putExtra("userEmail", user.getEmail());
                intent.putExtra("pass", user.getPass());
                intent.putExtra("gender", user.getGender());
                intent.putExtra("bloodType", user.getBloodType());
                intent.putExtra("name", user.getName());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("address", user.getAddress());
                intent.putExtra("image", user.getAddress());
                startActivity(intent);
                finish();
            }
        });
        reference = FirebaseFirestore.getInstance();
        message =new ArrayList<messageModel>();
        recyclerView3.setLayoutManager(new LinearLayoutManager(show_message.this));
        messageListAdpter =new  messageAdapter(show_message.this);
        recyclerView3.setAdapter(messageListAdpter);
        messageListAdpter.setCreator_email(user.getEmail());
        messageListAdpter.setAddress(user.getAddress());
        messageListAdpter.setAvailable(user.getAvailable());
        messageListAdpter.setBloodType(user.getBloodType());
        messageListAdpter.setGender(user.getGender());
        messageListAdpter.setName(user.getName());
        messageListAdpter.setPass(user.getPass());
        messageListAdpter.setPhone(user.getPhone());
        messageListAdpter.setImage("");
        messageListAdpter.setTotal_donated(user.getTotal_donated());
        messageListAdpter.setMessage(message);
        getrequest(reciver_email);


    }
    public void getrequest(String reciver_email){
        reference.collection("messages")
                .whereEqualTo("reciver_email",reciver_email )
                .whereEqualTo("approved","false")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        messageModel m;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            //id,text,date,creator_email,title,reciver_email,approved
                            m = new messageModel( documentSnapshot.getString("text"), documentSnapshot.getString("date"), documentSnapshot.getString("creator_email"), documentSnapshot.getString("title"),documentSnapshot.getString("reciver_email") , documentSnapshot.getString("approved"));
                            message.add(m);
                            messageListAdpter.notifyDataSetChanged();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_LONG).show();
                    }
                });
    }
}