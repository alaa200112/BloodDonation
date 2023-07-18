package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Models.hospitalModel;
import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    FirebaseFirestore reference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email_txt=(EditText) findViewById(R.id.Email);
        EditText Password_txt=(EditText) findViewById(R.id.password);
        Button lognBtn=(Button) findViewById(R.id.Login);
        TextView forget_txt=(TextView) findViewById(R.id.forgetPassword);
        TextView register_user=(TextView) findViewById(R.id.register_user);
        TextView register_hospital=(TextView) findViewById(R.id.register_hospital);
        progressDialog = new ProgressDialog(this);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        ///////////////////////////////////////////////////////////////Login////////////////////////////
        lognBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_txt.getText().toString();
                String password = Password_txt.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all data", Toast.LENGTH_SHORT).show();
                }
                else if(email.contains("@hospital")) {
                    progressDialog.show();
                    //to check email and pass
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(getApplicationContext(), "ana hena", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                    //to get information of hospital and go to home page
                                    reference = FirebaseFirestore.getInstance();
                                    reference.collection("hospital")
                                            .whereEqualTo("email", email)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    hospitalModel hospital;
                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                        hospital = new hospitalModel(documentSnapshot.getString("name"), documentSnapshot.getString("password"), documentSnapshot.getString("email"), documentSnapshot.getString("phone"), documentSnapshot.getString("address"), documentSnapshot.getString("postcode"), documentSnapshot.getString("rate"));
                                                        Toast.makeText(getApplicationContext(), "Welcome:  " + hospital.getName(), Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(Login.this, Hospital_Home.class);
                                                        intent.putExtra("userEmail", email);
                                                        intent.putExtra("name", hospital.getName());
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                }
                            });
                }
                else {
                    progressDialog.show();
                    //to check email and pass
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Login succesful", Toast.LENGTH_SHORT).show();
                                    //to get information of user and go to home page
                                    reference = FirebaseFirestore.getInstance();
                                    reference.collection("Users")
                                            .whereEqualTo("email", email)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    userModel user;
                                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                                        user = new userModel(documentSnapshot.getString("name"), documentSnapshot.getString("phone"), documentSnapshot.getString("email"), documentSnapshot.getString("pass"), documentSnapshot.getString("address"), documentSnapshot.getString("gender"),documentSnapshot.getString("bloodType"),documentSnapshot.getString("available"),documentSnapshot.getString("last_donated"),documentSnapshot.getString("total_donated"),documentSnapshot.getString("image"));
                                                        Toast.makeText(getApplicationContext(),"Welcome:  "+user.getName(),Toast.LENGTH_LONG).show();
                                                        Intent intent=new Intent(Login.this,HomeActivity.class);
                                                        intent.putExtra("userEmail", user.getEmail());
                                                        intent.putExtra("pass", user.getPass());
                                                        intent.putExtra("gender", user.getGender());
                                                        intent.putExtra("bloodType", user.getBloodType());
                                                        intent.putExtra("name", user.getName());
                                                        intent.putExtra("phone", user.getPhone());
                                                        intent.putExtra("address", user.getAddress());
                                                        intent.putExtra("image", user.getImage());
                                                        intent.putExtra("available", user.getAvailable());
                                                        intent.putExtra("last_donated", user.getLast_donated());
                                                        intent.putExtra("total_donated", user.getTotal_donated());
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });

                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Email or password inCorrect", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
        ///////////////////////////////////////////////////////////forget Password/////////////////////////////
        forget_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_txt.getText().toString();
                if (!email.isEmpty()) {
                    progressDialog.setTitle("Sending Mail");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    Toast.makeText(Login.this, "please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //////////////////////////////////////////////////////////Register/////////////////////////////////
        register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        register_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,hospital_register.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

