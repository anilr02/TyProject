package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
public class login extends AppCompatActivity {
    Button callSignUp, login_btn;
    TextInputLayout login_username, login_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        login_username= findViewById(R.id.logUsername);
        login_password= findViewById(R.id.logPassword);
        callSignUp= findViewById(R.id.signUpbtn);
        login_btn= findViewById(R.id.Signin);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() | !validatePassword()){

                }else{
                    checkUser();
                }
            }
        });
        //redirect from login to sign up
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername(){
        String val = login_username.getEditText().getText().toString();
        if (val.isEmpty()) {
            login_username.setError("Username cannot be empty");
            return false;
        } else {
            login_username.setError(null);
            login_username.setErrorEnabled(false);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = login_password.getEditText().getText().toString();
        if (val.isEmpty()) {
            login_password.setError("Password cannot be empty");
            return false;
        } else {
            login_password.setError(null);
            login_password.setErrorEnabled(false);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = login_username.getEditText().getText().toString().trim();
        String userPassword  = login_username.getEditText().getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
        //Query checkUserDatabase = reference.orderByChild("password").equalTo(userPassword);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    login_username.setError(null);
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    if (!Objects.equals(passwordFromDB, userPassword)) {
                        login_username.setError(null);
                        Intent intent = new Intent(login.this,positioncap.class);
                        startActivity(intent);
                    } else {
                        login_password.setError("Invalid Credentials");
                        login_password.requestFocus();
                    }
                }else{
                    login_username.setError("User does not exists");
                    login_username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public  void callforgetPassword(View view){
        startActivity(new Intent(getApplicationContext(),ForgetPassword.class));
    }
}
