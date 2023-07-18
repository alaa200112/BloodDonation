package com.example.blooddonationapp.Models;

import java.sql.Timestamp;
import java.util.Date;

public class userModel {
    private String name,phone,email,pass,address,gender,bloodType,available,total_donated,image;
    private String last_donated;
    public userModel(){
    }
    public userModel(String name, String phone, String email, String pass, String address, String image) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
        this.address = address;
        this.image = image;

    }
    public userModel(String name, String phone, String email, String pass, String address, String gender, String bloodType) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
        this.address = address;
        this.gender = gender;
        this.bloodType = bloodType;

    }

    public userModel(String name, String phone, String email, String pass, String address, String gender, String bloodType, String image) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
        this.address = address;
        this.gender = gender;
        this.bloodType = bloodType;
        this.image = image;

    }
    public userModel(String name, String phone, String email, String pass, String address, String gender, String bloodType,String available,String last_donated,String total_donated) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
        this.address = address;
        this.gender = gender;
        this.bloodType = bloodType;
        this.available=available;
        this.last_donated =last_donated;
        this.total_donated=total_donated;

    }
    public userModel(String name, String phone, String email, String pass, String address, String gender, String bloodType,String available,String last_donated,String total_donated,String image) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
        this.address = address;
        this.gender = gender;
        this.bloodType = bloodType;
        this.available=available;
        this.last_donated =last_donated;
        this.total_donated=total_donated;
        this.image=image;

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getAvailable() {
        return available;
    }

    public String getTotal_donated() {
        return total_donated;
    }

    public String getImage() {
        return image;
    }

    public String getLast_donated() {
        return last_donated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setLast_donated(String last_donated) {
        this.last_donated = last_donated;
    }
}
