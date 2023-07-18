package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Adapter.requestAdapter;
import com.example.blooddonationapp.Models.requestModel;
import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showRequest extends AppCompatActivity {
    private RecyclerView recyclerView2;
    List<requestModel> request;
    FirebaseFirestore reference;
    String reciver_email;
    TextView undoTextBtn;

    requestAdapter requestListAdpter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request);
        recyclerView2 = findViewById(R.id.recyclerView2);
        userModel user1=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"));


        reciver_email=getIntent().getExtras().getString("userEmail");
       // userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"),getIntent().getExtras().getString("available"),getIntent().getExtras().getString("last_donated"),getIntent().getExtras().getString("total_donated"));

        reference = FirebaseFirestore.getInstance();
        request=new ArrayList<requestModel>();
        recyclerView2.setLayoutManager(new LinearLayoutManager(showRequest.this));
        requestListAdpter=new requestAdapter(showRequest.this);
        recyclerView2.setAdapter( requestListAdpter);
        requestListAdpter.setCreator_email(user1.getEmail());
        requestListAdpter.setPhone_num(user1.getPhone());
        requestListAdpter.setRequest(request);
        getrequest(reciver_email);


    }
    public void getrequest(String reciver_email){
        reference.collection("requests")
                .whereEqualTo("reciver_email",reciver_email )
                .whereEqualTo("approved","false")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        requestModel r;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            //id,text,date,creator_email,title,reciver_email,approved
                            r = new requestModel( documentSnapshot.getString("text"), documentSnapshot.getString("date"), documentSnapshot.getString("creator_email"), documentSnapshot.getString("title"),documentSnapshot.getString("reciver_email") , documentSnapshot.getString("approved"));
                            request.add(r);
                            requestListAdpter.notifyDataSetChanged();
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