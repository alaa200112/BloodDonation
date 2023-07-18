/*package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Adapter.donarsAdapter;
import com.example.blooddonationapp.Adapter.postAdapter;
import com.example.blooddonationapp.Models.postModel;
import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showDonars extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<userModel> donars;
    FirebaseFirestore reference;
    String blood;
    ProgressDialog pd;
    donarsAdapter donarListAdpter;
    TextView undoTextBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donars);
        Button b=(Button)findViewById(R.id.btnSearch);
        userModel user1=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"));

        undoTextBtn = (TextView) findViewById(R.id.undo);
        undoTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(showDonars.this,HomeActivity.class);
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
        pd=new ProgressDialog(this);
        Spinner bloodType_txt=(Spinner) findViewById(R.id.inputBloodGroup);
        userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"));
        b.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                recyclerView = findViewById(R.id.recyclerView);
                reference = FirebaseFirestore.getInstance();
                donars=new ArrayList<userModel>();
                recyclerView.setLayoutManager(new LinearLayoutManager(showDonars.this));
                donarListAdpter=new donarsAdapter(showDonars.this);
                donarListAdpter.setCreator_email(user.getEmail());
                recyclerView.setAdapter(donarListAdpter);
                donarListAdpter.setDonars(donars);
             donarListAdpter.setCreator_email(user.getEmail());
               getdonar(bloodType_txt.getSelectedItem().toString());

            }
        });

    }
    //to get donars from firebase
    public void getdonar(String bloodType){
        reference.collection("Users")
                .whereEqualTo("bloodType", bloodType)
                .whereEqualTo("available","true")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        userModel user;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            user = new userModel(documentSnapshot.getString("name"), documentSnapshot.getString("phone"), documentSnapshot.getString("email"), documentSnapshot.getString("pass"), documentSnapshot.getString("address"), documentSnapshot.getString("gender"),documentSnapshot.getString("bloodType"),documentSnapshot.getString("available"),documentSnapshot.getString("last_donated"),documentSnapshot.getString("total_donated"));
                            donars.add(user);
                            donarListAdpter.notifyDataSetChanged();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_LONG).show();
                    }
                });
    }
}*/

package com.example.blooddonationapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Adapter.donarsAdapter;
import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showDonars extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<userModel> donars;
    FirebaseFirestore reference;
    String blood;
    TextView undoTextBtn;
    ProgressDialog pd;
    donarsAdapter donarListAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donars);
        Button b=(Button)findViewById(R.id.btnSearch);
        pd=new ProgressDialog(this);
        String user_email=getIntent().getExtras().getString("userEmail");



        Spinner bloodType_txt=(Spinner) findViewById(R.id.inputBloodGroup);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView = findViewById(R.id.recyclerView);
                reference = FirebaseFirestore.getInstance();
                donars=new ArrayList<userModel>();
                recyclerView.setLayoutManager(new LinearLayoutManager(showDonars.this));
                donarListAdpter=new donarsAdapter(showDonars.this,user_email);
                recyclerView.setAdapter(donarListAdpter);
                donarListAdpter.setDonars(donars);
                getdonar(bloodType_txt.getSelectedItem().toString());

            }
        });

    }
    //to get donars from firebase
    public void getdonar(String bloodType){
        reference.collection("Users")
                .whereEqualTo("bloodType", bloodType)
                .whereEqualTo("available","true")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        userModel user;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            user = new userModel(documentSnapshot.getString("name"), documentSnapshot.getString("phone"), documentSnapshot.getString("email"), documentSnapshot.getString("pass"), documentSnapshot.getString("address"), documentSnapshot.getString("gender"),documentSnapshot.getString("bloodType"),documentSnapshot.getString("available"),documentSnapshot.getString("last_donated"),documentSnapshot.getString("total_donated"));
                            donars.add(user);
                            donarListAdpter.notifyDataSetChanged();
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