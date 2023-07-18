package com.example.blooddonationapp.Models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class hospitalModel  {
    private String name,password,email,phone,address,postcode,rate;
    private float latitude,longtitude;
    public hospitalModel(){}
    public hospitalModel(String name,String password,String email,String phone,String address,String postcode,String rate,float latitude,float longtitude) {
        this.name=name;
        this.password=password;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.postcode=postcode;
        this.rate=rate;
        this.latitude=latitude;
        this.longtitude=longtitude;

    }

    public hospitalModel(String name, String password, String email, String phone, String address, String postcode, String rate) {
        this.name=name;
        this.password=password;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.postcode=postcode;
        this.rate=rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    //Save date in hospital:
    public void saveData(String name,String password,String email,String phone,String address,String postcode,String rate,float latitude,float longtitude){
        FirebaseAuth firebaseAuth;
        FirebaseFirestore firebaseFirestore;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult){
                        firebaseFirestore.collection("hospital")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(new hospitalModel(name, password, email, phone, address, postcode,rate, latitude, longtitude));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }

}

