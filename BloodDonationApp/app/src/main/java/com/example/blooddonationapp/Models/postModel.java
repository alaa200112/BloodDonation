package com.example.blooddonationapp.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class postModel {
    private String text,date,creator_email,title,Name;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fp;
static  int x=1;
    //private int id;
//    public postModel()
//    {
//      id=0;
//    }
    public postModel()
    {

    }
    public postModel(String text, String date, String creator_email, String title,String Name)
    {

        this.text=text;
        this.date=date;
        this.creator_email=creator_email;
        this.title = title;
        this.Name=Name;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void saveData(String id,String text, String date, String creator_email, String title, String Name){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .document(FirebaseAuth.getInstance().getUid()+"_"+x)
                .set(new postModel(text,date,creator_email,title,Name));
        x++;
    }
}
