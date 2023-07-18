package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Adapter.requestAdapter;
import com.example.blooddonationapp.Models.requestModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RequestHospitalActivity extends AppCompatActivity {
    private RecyclerView recyclerView2;
    List<requestModel> request;
    FirebaseFirestore reference;
    String reciver_email;
    TextView undoTextBtn;
    requestAdapter requestListAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_hospital);
        recyclerView2 = findViewById(R.id.recyclerView2);
        undoTextBtn = (TextView) findViewById(R.id.undo);
        undoTextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });
        reciver_email=getIntent().getExtras().getString("hospitalEmail");
        reference = FirebaseFirestore.getInstance();
        request=new ArrayList<requestModel>();
        recyclerView2.setLayoutManager(new LinearLayoutManager(RequestHospitalActivity.this));
        requestListAdpter=new requestAdapter(RequestHospitalActivity.this);
        recyclerView2.setAdapter( requestListAdpter);
        requestListAdpter.setCreator_email(reciver_email);
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