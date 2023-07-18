package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonationapp.Models.hospitalModel;

public class hospital_register extends AppCompatActivity {
    TextView name_txt,phone_txt,address_txt,email_txt,pass_txt,password_confirm_txt,pstcode_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_register);
        name_txt = (TextView) findViewById(R.id.input_fullName);
        address_txt = (TextView) findViewById(R.id.inputAddress);
        phone_txt = (TextView) findViewById(R.id.inputMobile);
        email_txt = (TextView) findViewById(R.id.input_userEmail);
        pass_txt = (TextView) findViewById(R.id.input_password);
        password_confirm_txt = (TextView) findViewById(R.id.input_password_confirm);
        pstcode_txt = (TextView) findViewById(R.id.inputcode);
        Button register = (Button) findViewById(R.id.button_register);
        TextView loginTxt=(TextView) findViewById(R.id.Logintxt);
        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(hospital_register.this,Login.class);
                startActivity(intent);
                finish();
            }
            //14 Mohamed El Sebaei, Haykstep, Qism El Nozha, Cairo Governorate
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_txt.getText().toString();
                String phone = phone_txt.getText().toString();
                String address = address_txt.getText().toString();
                String email = email_txt.getText().toString();
                String password = pass_txt.getText().toString();
                String password_confirm = password_confirm_txt.getText().toString();
                String pstcode = pstcode_txt.getText().toString();
                if(name.equals("")||phone.equals("")||address.equals("")||email.equals("")||password.equals("")||password_confirm.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter all information",Toast.LENGTH_LONG).show();
                }
                else{
                    if(password.equals(password_confirm))
                    {
                        hospitalModel hm=new hospitalModel();

                        hm.saveData(name,password,email,phone,address,pstcode,"0",0,0);

                        Toast.makeText(getApplicationContext(), "SignUp Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(hospital_register.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Password not match confirm password",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}