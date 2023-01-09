package com.example.myapplication;

import static com.example.myapplication.R.id.phoneNo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import android.os.Bundle;

public class signup extends AppCompatActivity {
    TextInputLayout regUsername, regPhone, regEmail, regPassword;
    Button callSignIn, regBtn;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        //hooks to all xml elements to sign up
        regUsername = findViewById(R.id.Username);
        regPhone = findViewById(R.id.phoneNo);
        regEmail = findViewById(R.id.Email);
        regPassword = findViewById(R.id.Password);
        regBtn = findViewById(R.id.chip2);
        callSignIn = findViewById(R.id.signInbtn);
        //signup button
        regBtn.setOnClickListener(new View.OnClickListener() {
            private Boolean validateUsername () {
                String val = regUsername.getEditText().getText().toString().trim();
                //String checkspaces = "Aw{1,15}z";

                if (val.isEmpty()) {
                    regUsername.setError("Field cannot be empty");
                    return false;
                } else if (val.length() >=15){
                    regUsername.setError("Username too long");
                    return false;
                } else {
                    regUsername.setError(null);
                    regUsername.setErrorEnabled(false);
                    return true;
                }
            }

            private Boolean validatePhone () {
                String val = regPhone.getEditText().getText().toString();
                String checkspaces = "Aw{1,20}z";
                if (val.isEmpty()) {
                    regPhone.setError("Field cannot be empty");
                    return false;
                } else if (!val.matches(checkspaces)) {
                    regEmail.setError("White Spaces are not allowed");
                    return false;
                }
                else if (val.length() >=10) {
                    regUsername.setError("Invalid Phone Number");
                    return false;
                }
                else {
                    regPhone.setError(null);
                    regPhone.setErrorEnabled(false);
                    return true;
                }
            }

            private Boolean validateEmail () {
                String val = regEmail.getEditText().getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (val.isEmpty()) {
                    regEmail.setError("Field cannot be empty");
                    return false;
                } else if (!val.matches(emailPattern)){
                    regEmail.setError("Invalid email address");
                    return false;
                } else {
                    regEmail.setError(null);
                    regEmail.setErrorEnabled(false);
                    return true;
                }
            }

            private Boolean validatePassword () {
                String val = regPassword.getEditText().getText().toString();
                String passwordVal = "^" +
                        //"(?=.*[0-9])" +         //at least 1 digit
                        //"(?=.*[a-z])" +         //at least 1 lower case letter
                        "(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{8,}" +               //at least 8 characters
                        "$";

                if (val.isEmpty()) {
                    regPassword.setError("Field cannot be empty");
                    return false;
                } else if (!val.matches(passwordVal)){
                    regPassword.setError("Password is too weak");
                    return false;
                } else {
                    regPassword.setError(null);
                    regPassword.setErrorEnabled(false);
                    return true;
                }
            }

            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                if( !validateUsername() | !validatePassword() | !validatePhone() | !validateEmail())
                {
                    return;
                }

                //gets all values
                String username = regUsername.getEditText().getText().toString();
                String phone = regPhone.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                HelperClass helperClass = new HelperClass(username , phone , email , password);
                reference.child(username).setValue(helperClass);
                Toast.makeText(signup.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });

        callSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
    }
}