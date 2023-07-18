package com.example.blooddonationapp.Models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class countDonars extends ViewModel {
   //public static int count=0;
   public MutableLiveData<Integer> count=new MutableLiveData<>();
    public countDonars(){
       count.setValue(0);
    }
    public void increment(){
       count.setValue(count.getValue() + 1);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
