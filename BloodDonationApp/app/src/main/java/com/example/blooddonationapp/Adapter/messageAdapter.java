package com.example.blooddonationapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.blooddonationapp.Models.messageModel;
import com.example.blooddonationapp.Models.requestModel;
import com.example.blooddonationapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.messageListHolder> {

    private List<messageModel> message=new ArrayList<>();
    Context context;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    String creator_email;
    String name,phone,pass,address,gender,bloodType,available,total_donated,image;
    static  int x=1;
    public messageAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public messageAdapter.messageListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new messageAdapter.messageListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull messageListHolder holder, int position) {
        holder.title_id.setText(message.get(position).getTitle());
        holder.des_id.setText(message.get(position).getText());
        holder.reqstUser.setText(message.get(position).getCreator_email());
        holder.date_id.setText(message.get(position).getDate());
    }


    public void setCreator_email(String creator_email) {

        this.creator_email = creator_email;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setTotal_donated(String total_donated) {
        this.total_donated = total_donated;
    }

    @Override
    public int getItemCount() {
        return message.size();
    }
    public class messageListHolder extends RecyclerView.ViewHolder{
        TextView title_id ,des_id,reqstUser,date_id;
        CheckBox ok;
        public messageListHolder(@NonNull View itemView) {
            super(itemView);
            title_id =itemView.findViewById(R.id.title_id);
            des_id=itemView.findViewById(R.id.des_id);
            reqstUser=itemView.findViewById(R.id.reqstUser);
            date_id=itemView.findViewById(R.id.date_id);
            ok=itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    messageModel meeee = message.get(position);
                    Boolean check=ok.isChecked();
                    if(check.equals(true)){
                        updateapproved(meeee.getCreator_email(),creator_email);
                        updateus();
                        Toast.makeText(context.getApplicationContext(), "succed: ",Toast.LENGTH_LONG).show();
                    }


                }
            });
        }
    }
    public void setMessage(List<messageModel>message){
        this.message=message;
        notifyDataSetChanged();
    }
    private void updateapproved(String ce ,String ri)
    {
        // Get a reference to the Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Set the email value to search for
        String c_email = ce;
        String ri_email=ri;

// Query the "requests" collection for documents with matching email value
        db.collection("messages")
                .whereEqualTo("creator_email", c_email).whereEqualTo("reciver_email",ri_email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        // Check if there is exactly one matching document
                        if (querySnapshot.size() == 1) {
                            // Get the document ID of the first (and only) matching document
                            String requestId = querySnapshot.getDocuments().get(0).getId();

                            // Update the document with the new field value (using the code from the previous example)
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("approved", true);

                            db.collection("messages").document(requestId)
                                    .update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Handle the success
                                            // Log.d(TAG, "Request updated successfully.");
                                            Toast.makeText(context.getApplicationContext(), "message updated successfully. ",Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle the error
                                            Toast.makeText(context.getApplicationContext(), "Error updating message: " + e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    });

                        } else if (querySnapshot.size() > 1) {
                            // Handle the case where there are multiple matching documents
                            Toast.makeText(context.getApplicationContext(), "Multiple message found with the same email value.",Toast.LENGTH_LONG).show();
                        } else {
                            // Handle the case where there are no matching documents
                            Toast.makeText(context.getApplicationContext(), "No message found with the given email value.",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(context.getApplicationContext(), "Error querying message collection: " + e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void updateus()
    {
        String userId="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", name);
        userMap. put("address",address );
        userMap. put("phone",phone );
        userMap. put("bloodType",bloodType);
        userMap. put("gender", gender);
        userMap. put("email",creator_email);
        userMap. put("pass",pass);
        userMap. put("image","");
        userMap. put("last_donated",formattedDate);
        userMap. put("available","false" );
        userMap.put("total_donated",String.valueOf(x));
        x++;
        // userMap. put("pass", pass);
        db.collection("Users")
                .document(userId)
                .set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "DocumentSnapshot successfully written.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"error.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
