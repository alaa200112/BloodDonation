package com.example.blooddonationapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.FormDonorActivity;
import com.example.blooddonationapp.FormFilter;
import com.example.blooddonationapp.Models.postModel;
import com.example.blooddonationapp.Models.requestModel;
import com.example.blooddonationapp.Models.userModel;
import com.example.blooddonationapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class postAdapter extends RecyclerView.Adapter<postAdapter.postListHolder>{
    private List<postModel> posts=new ArrayList<postModel>();
    Context context;
    userModel user;
    String creator_email;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    public postAdapter(Context context, userModel user){
        this.context=context;
        this.user=user;
    }


    public void setCreator_email(String creator_email) {

        this.creator_email = creator_email;
    }

    @NonNull
    @Override
    public postListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new postListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull postListHolder holder, int position) {
        holder.Name.setText("Name: "+posts.get(position).getName());
        holder.description.setText("Description: "+posts.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    public class postListHolder extends RecyclerView.ViewHolder {
        TextView Name,description;

        CheckBox ok;
        public postListHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.Name);
            description=itemView.findViewById(R.id.contentPost);
            ok=itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    postModel selectedPost = posts.get(position);
                    Boolean check=ok.isChecked();
                    if(check.equals(true)){
                        /**
                        Intent intent=new Intent(context, FormFilter.class);
                        intent.putExtra("formattedDate",formattedDate);
                        intent.putExtra("creator_email",creator_email);
                        intent.putExtra("recive",selectedPost.getCreator_email());
                        intent.putExtra("userEmail", user.getEmail());
                        intent.putExtra("pass", user.getPass());
                        intent.putExtra("gender", user.getGender());
                        intent.putExtra("bloodType", user.getBloodType());
                        intent.putExtra("name", user.getName());
                        intent.putExtra("phone", user.getPhone());
                        intent.putExtra("address", user.getAddress());
                        intent.putExtra("image", user.getAddress());
                        intent.putExtra("available", user.getAvailable());
                        context.startActivity(intent);
                         **/
                        Intent intent=new Intent(context, FormDonorActivity.class);
                        intent.putExtra("formattedDate",formattedDate);
                        intent.putExtra("creator_email",creator_email);
                        intent.putExtra("recive",selectedPost.getCreator_email());
                        intent.putExtra("userEmail", user.getEmail());
                        intent.putExtra("pass", user.getPass());
                        intent.putExtra("gender", user.getGender());
                        intent.putExtra("bloodType", user.getBloodType());
                        intent.putExtra("name", user.getName());
                        intent.putExtra("phone", user.getPhone());
                        intent.putExtra("address", user.getAddress());
                        intent.putExtra("image", user.getAddress());
                        intent.putExtra("last_donated",user.getLast_donated());
                        intent.putExtra("total_donated",user.getTotal_donated());
                        intent.putExtra("available", user.getAvailable());
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
    public void setPosts(List<postModel>posts){
        this.posts=posts;
        notifyDataSetChanged();
    }
}
