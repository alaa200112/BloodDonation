package com.example.blooddonationapp.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class messageModel {
    private String text,date,creator_email,title,reciver_email,approved;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fp;
    static  int x=1;
    public messageModel()
    {

    }
    public messageModel( String text, String date, String creator_email, String title, String reciver_email, String approved)
    {

        this.text=text;
        this.date=date;
        this.creator_email=creator_email;
        this.title = title;
        this.reciver_email=reciver_email;
        this.approved=approved;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreator_email() {
        return creator_email;
    }

    public void setCreator_email(String creator_email) {
        this.creator_email = creator_email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReciver_email() {
        return reciver_email;
    }

    public void setReciver_email(String reciver_email) {
        this.reciver_email = reciver_email;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public void saveData( String text, String date, String creator_email, String title, String reciver_email, String approved){

        fp=FirebaseFirestore.getInstance();
        fp.collection("messages")
                .document(FirebaseAuth.getInstance().getUid()+"_"+x)
                .set(new messageModel(text,date,creator_email,title,reciver_email,approved));
        x++;
    }
}

