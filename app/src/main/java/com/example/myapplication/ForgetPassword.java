package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.core.view.View;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {
    //variables
    //ScrollView scrollView;
    TextInputLayout phoneNumber;
    CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //hooks
        //scrollView=findViewById(R.id.forget_password_screen_scroll_view);
        countryCodePicker=findViewById(R.id.country_code_picker);
        phoneNumber=findViewById(R.id.forget_password_phone_number);

    }
    public void callVerifyOTPScreen(View view){
        //Validate fields
        if(!ValidatePhoneNumber()){
            return;
        }//Validation succeeded and move to next screen to verify phone number and save data

        //get all values passed from sign up screen using Intent

        String _getUserEnteredPhoneNumber= phoneNumber.getEditText().getText().toString().trim();//get phone number
        String _phone = "+"+countryCodePicker.getFullNumber()+_getUserEnteredPhoneNumber;

        Intent intent= new Intent(getApplicationContext(),VerifyOTP.class);

        intent.putExtra("phone", _phone);
    }

    private boolean ValidatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}