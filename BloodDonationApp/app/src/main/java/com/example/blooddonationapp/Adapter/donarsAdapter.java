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

import com.example.blooddonationapp.Models.requestModel;
import com.example.blooddonationapp.Models.userModel;
import com.example.blooddonationapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class donarsAdapter extends RecyclerView.Adapter<donarsAdapter.donarListHolder>{
    private List<userModel> donars=new ArrayList<>();
    Context context;
    String creator_email;
    Date c = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);
    public donarsAdapter(Context context,String userEmail){
        this.context=context;
        setCreator_email(userEmail);
    }

    @NonNull
    @Override
    public donarListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new donarListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donar,parent,false));
    }
    public void setCreator_email(String creator_email) {

        this.creator_email = creator_email;
    }

    @Override
    public void onBindViewHolder(@NonNull donarListHolder holder, int position) {
        holder.donarName.setText("Donar Name: "+donars.get(position).getName());
        holder.donarAddress.setText("Address: "+donars.get(position).getAddress());
        holder.bloodType.setText("Blood Type: "+donars.get(position).getBloodType());
    }


    @Override
    public int getItemCount() {
        return donars.size();
    }

    public class donarListHolder extends RecyclerView.ViewHolder {
        TextView donarName,donarAddress,bloodType;
        CheckBox ok;
        public donarListHolder(@NonNull View itemView) {
            super(itemView);
            donarName=itemView.findViewById(R.id.donorName);
            donarAddress=itemView.findViewById(R.id.donorAddress);
            bloodType=itemView.findViewById(R.id.bloodtype);
            ok=itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    userModel donar = donars.get(position);
                    Boolean check=ok.isChecked();
                    if(check.equals(true)){
                        requestModel rm= new requestModel();
                        rm.saveData("I need your help",formattedDate,creator_email,"Request Help",donar.getEmail(),"false");
                        Toast.makeText(context,"suuucced",Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(context.getApplicationContext(), "welcom: "+donar.getName(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void setDonars(List<userModel>donars){
        this.donars=donars;
        notifyDataSetChanged();
    }
}
