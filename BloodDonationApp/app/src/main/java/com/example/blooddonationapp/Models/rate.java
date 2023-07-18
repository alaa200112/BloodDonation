package com.example.blooddonationapp.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class rate {
    private String host_email,user_email,rate;
    private int id;
    public rate() {
        id=0;
    }

    public rate(int id, String host_email, String user_email, String rate) {
        this.id = id;
        this.host_email = host_email;
        this.user_email = user_email;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost_email() {
        return host_email;
    }

    public void setHost_email(String host_email) {
        this.host_email = host_email;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void saveData(int id, String host_email, String user_email, String rate){
        FirebaseAuth firebaseAuth;
        FirebaseFirestore fr;
        firebaseAuth=FirebaseAuth.getInstance();
        fr=FirebaseFirestore.getInstance();
        fr.collection("rates")
                .document(FirebaseAuth.getInstance().getUid())
                .set(new rate(id,host_email,user_email,rate));
    }
}
