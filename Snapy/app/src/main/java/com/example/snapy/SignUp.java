package com.example.snapy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    TextView signUptextview,gender;
    EditText name,email,dateOfBirth;
    Button signUpButtton;
    RadioButton male,female,other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUptextview=findViewById(R.id.textView2);
        name=findViewById(R.id.name);
        email=findViewById(R.id.editTextTextEmailAddress);
        dateOfBirth=findViewById(R.id.dateOfBirth);
        gender=findViewById(R.id.gender);
        male=findViewById(R.id.simpleRadioButton);
        female=findViewById(R.id.simpleRadioButton1);
        other=findViewById(R.id.simpleRadioButton2);
        signUpButtton=findViewById(R.id.signUpButton);
    }
}