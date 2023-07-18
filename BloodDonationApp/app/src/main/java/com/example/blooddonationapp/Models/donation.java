package com.example.blooddonationapp.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class donation {
    private String host_email,user_email,donation_date;
    private int id;
    public donation() {
        id=0;
    }

    public donation(int id, String host_email, String user_email, String donation_date) {
        this.id = id;
        this.host_email = host_email;
        this.user_email = user_email;
        this.donation_date = donation_date;
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

    public String getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(String donation_date) {
        this.donation_date = donation_date;
    }
    public void saveData(int id, String host_email, String user_email, String donation_date){
        FirebaseAuth firebaseAuth;
        FirebaseFirestore fd;
        firebaseAuth=FirebaseAuth.getInstance();
        fd=FirebaseFirestore.getInstance();
        fd.collection("donation")
                .document(FirebaseAuth.getInstance().getUid())
                .set(new donation(id,host_email,user_email,donation_date));
    }
}
