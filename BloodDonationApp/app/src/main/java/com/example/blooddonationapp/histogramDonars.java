package com.example.blooddonationapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Models.countDonars;
import com.example.blooddonationapp.Models.userModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
public class histogramDonars extends AppCompatActivity {
    ArrayList<String> bloodType = new ArrayList<String>() {
        {
            add("A+");
            add("A-");
            add("B+");
            add("B-");
            add("O+");
            add("O-");
            add("AB+");
            add("AB-");
        }
    };
    ArrayList <BarEntry>barChartArray;
    FirebaseFirestore reference;
    List<Integer>totalcount=new ArrayList<Integer>(){
        {
            add(0);
            add(0);
            add(0);
            add(0);
            add(0);
            add(0);
            add(0);
            add(0);
        }
    };
    float x=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram_donars);
        barChartArray=new ArrayList<BarEntry>();
        reference = FirebaseFirestore.getInstance();
        FirebaseFirestore.getInstance().collection("Users")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            String bloodtype=String.valueOf(documentSnapshot.getString("bloodType"));
                            //to know number of users in specfic bloodtype
                            switch (bloodtype)
                            {
                                case "A+":
                                    totalcount.set(0, totalcount.get(0) + 1);
                                    break;
                                case "A-":
                                    totalcount.set(1, totalcount.get(1) + 1);
                                    break;
                                case "B+":
                                    totalcount.set(2, totalcount.get(2) + 1);
                                    break;
                                case "B-":
                                    totalcount.set(3, totalcount.get(3) + 1);
                                    break;
                                case "O+":
                                    totalcount.set(4, totalcount.get(4) + 1);
                                    break;
                                case "O-":
                                    totalcount.set(5, totalcount.get(5) + 1);
                                    break;
                                case "AB+":
                                    totalcount.set(6, totalcount.get(6) + 1);
                                    break;
                                case "AB-":
                                    totalcount.set(7, totalcount.get(7) + 1);
                                    break;
                            }
                        }
                        getData();
                        BarChart barChart=findViewById(R.id.BarChart);
                        BarDataSet barDataSet=new BarDataSet(barChartArray,"Blood Type");
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(20f);
                        BarData barData=new BarData(barDataSet);
                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.getDescription().setText("Number of donars");
                        XAxis xAxis=barChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(bloodType));
                        xAxis.setPosition(XAxis.XAxisPosition.TOP);
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(bloodType.size());
                        xAxis.setLabelRotationAngle(270);
                        barChart.animateY(2000);
                        barChart.invalidate();
                    }
                });
    }
    private void getData() {
        barChartArray = new ArrayList();
        int x=0;
        for(int f: totalcount)
        {
            barChartArray.add(new BarEntry(x++,f));
        }
    }

}