package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Adapter.postAdapter;
import com.example.blooddonationapp.Models.postModel;
import com.example.blooddonationapp.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showPosts extends AppCompatActivity {
    private RecyclerView recyclerView;
    FirebaseFirestore reference;
    List<postModel> posts;
    postAdapter postListAdapter;
    private TextView undoTextBtn, addTextBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_posts);

        recyclerView = findViewById(R.id.recyclerViewpost);
        reference = FirebaseFirestore.getInstance();

        addTextBtn = (TextView) findViewById(R.id.add);


        userModel user=new userModel( getIntent().getExtras().getString("name"),getIntent().getExtras().getString("phone"),getIntent().getExtras().getString("userEmail"),getIntent().getExtras().getString("pass"),getIntent().getExtras().getString("address"),getIntent().getExtras().getString("gender"),getIntent().getExtras().getString("bloodType"),getIntent().getExtras().getString("image"));



        addTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(showPosts.this,w_post.class);
                intent.putExtra("userEmail", user.getEmail());
                intent.putExtra("pass", user.getPass());
                intent.putExtra("gender", user.getGender());
                intent.putExtra("bloodType", user.getBloodType());
                intent.putExtra("name", user.getName());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("address", user.getAddress());
                intent.putExtra("image", user.getAddress());
                startActivity(intent);
                finish();
            }
        });


    posts=new ArrayList<postModel>();
        recyclerView.setLayoutManager(new LinearLayoutManager(showPosts.this));
    postListAdapter=new postAdapter( showPosts.this,user);
        postListAdapter.setCreator_email(user.getEmail());
        recyclerView.setAdapter(postListAdapter);
        postListAdapter.setPosts(posts);
    getPosts();
    }
    public void getPosts(){
        reference.collection("posts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        postModel post;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            post = new postModel(documentSnapshot.getString("text"), documentSnapshot.getString("date"), documentSnapshot.getString("creator_email"),documentSnapshot.getString("title"),documentSnapshot.getString("name"));
                            posts.add(post);
                            postListAdapter.notifyDataSetChanged();
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