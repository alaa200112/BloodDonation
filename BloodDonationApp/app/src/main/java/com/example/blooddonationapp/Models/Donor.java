package com.example.blooddonationapp.Models;

public class Donor {
    private String name;
    private double latitude;
    private double longitude;
    private String phone;

    public Donor(String name, double latitude, double longitude,String phone) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone=phone;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }
}
