package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView name_txt,phone_txt,address_txt,email_txt,pass_txt,password_confirm_txt;
    //the date of last donated:
    ///////////////////////////
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    ///////////////////////////////
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_txt=(TextView) findViewById(R.id.input_fullName);
        address_txt=(TextView) findViewById(R.id.inputAddress);
        phone_txt=(TextView) findViewById(R.id.inputMobile);
        email_txt=(TextView) findViewById(R.id.input_userEmail);
        pass_txt=(TextView) findViewById(R.id.input_password);
        password_confirm_txt=(TextView) findViewById(R.id.input_password_confirm);
        Spinner gender_txt=(Spinner) findViewById(R.id.gender);
        Spinner bloodType_txt=(Spinner) findViewById(R.id.inputBloodGroup);
        Button register=(Button) findViewById(R.id.button_register);
        progressDialog=new ProgressDialog(this);
        /////////////////////////////////////////////////Register///////////////////////////////////////
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=name_txt.getText().toString();
                String phone=phone_txt.getText().toString();
                String address=address_txt.getText().toString();
                String email=email_txt.getText().toString();
                String password=pass_txt.getText().toString();
                String password_confirm=password_confirm_txt.getText().toString();
                String bloodType=bloodType_txt.getSelectedItem().toString();
                String gender=gender_txt.getSelectedItem().toString();
                if(name.equals("")||phone.equals("")||address.equals("")||email.equals("")||password.equals("")||password_confirm.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter all information",Toast.LENGTH_LONG).show();
                }
                else{
                    if(password.equals(password_confirm))
                    {
                        saveData(name,phone,email,password,address,gender,bloodType,"true","0","0");

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Password not match confirm password",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //to login
        TextView loginTxt=(TextView) findViewById(R.id.Logintxt);
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();

            }
        });

    }
    public void saveData(String name, String phone, String email, String pass, String address, String gender, String bloodType,String available,String last_donated,String total_donated){
        FirebaseAuth firebaseAuth;
        FirebaseFirestore firebaseFirestore;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        firebaseFirestore.collection("Users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(new userModel(name,phone,email,pass,address,gender,bloodType,available,last_donated,total_donated,""));
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                });

    }
}