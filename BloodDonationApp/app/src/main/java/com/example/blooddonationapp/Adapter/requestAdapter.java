
package com.example.blooddonationapp.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Models.messageModel;
import com.example.blooddonationapp.Models.requestModel;

import com.example.blooddonationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class requestAdapter extends RecyclerView.Adapter<requestAdapter.requestListHolder> {

    private List<requestModel> request=new ArrayList<>();
    Context context;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    String creator_email;

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    String phone_num;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    public requestAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public requestAdapter.requestListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new requestAdapter.requestListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull requestListHolder holder, int position) {
        holder.title_id.setText(request.get(position).getTitle());
        holder.des_id.setText(request.get(position).getText());
        holder.reqstUser.setText(request.get(position).getCreator_email());
        holder.date_id.setText(request.get(position).getDate());

    }


    public void setCreator_email(String creator_email) {

        this.creator_email = creator_email;
    }

    @Override
    public int getItemCount() {
        return request.size();
    }
    public class requestListHolder extends RecyclerView.ViewHolder{
        TextView title_id ,des_id,reqstUser,date_id;
        CheckBox ok;
        public requestListHolder(@NonNull View itemView) {
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
                    requestModel req = request.get(position);

                    Boolean check=ok.isChecked();
                    if(check.equals(true)){
                        messageModel messa= new messageModel();

                       messa.saveData("Thank you for help, I apply your request,My Phone:"+phone_num,formattedDate,creator_email,"Request Applied",req.getCreator_email(),"false");
                        updateapproved(req.getCreator_email(),creator_email);
                        Toast.makeText(context.getApplicationContext(), "succed: ",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }
    public void setRequest(List<requestModel>request){
        this.request=request;
        notifyDataSetChanged();
    }
private void updateapproved(String ce,String ri)
{
    // Get a reference to the Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

// Set the email value to search for
    String c_email = ce;
    String ri_email=ri;

// Query the "requests" collection for documents with matching email value
    db.collection("requests")
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

                        db.collection("requests").document(requestId)
                                .update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Handle the success
                                       // Log.d(TAG, "Request updated successfully.");
                                        Toast.makeText(context.getApplicationContext(), "Request updated successfully. ",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle the error
                                        Toast.makeText(context.getApplicationContext(), "Error updating request: " + e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });

                    } else if (querySnapshot.size() > 1) {
                        // Handle the case where there are multiple matching documents
                        Toast.makeText(context.getApplicationContext(), "Multiple requests found with the same email value.",Toast.LENGTH_LONG).show();
                    } else {
                        // Handle the case where there are no matching documents
                        Toast.makeText(context.getApplicationContext(), "No request found with the given email value.",Toast.LENGTH_LONG).show();
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Handle the error
                    Toast.makeText(context.getApplicationContext(), "Error querying requests collection: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

}





}
