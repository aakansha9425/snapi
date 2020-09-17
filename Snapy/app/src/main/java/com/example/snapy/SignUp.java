package com.example.snapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    TextView signUptextview, gender;
    EditText name, email, dateOfBirth, password;
    Button signUpButtton;
    RadioButton male, female, other;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUptextview = findViewById(R.id.textView2);
        name = findViewById(R.id.name);
        email = findViewById(R.id.editTextTextEmailAddress);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        gender = findViewById(R.id.gender);
        male = findViewById(R.id.simpleRadioButton);
        female = findViewById(R.id.simpleRadioButton1);
        other = findViewById(R.id.simpleRadioButton2);
        signUpButtton = findViewById(R.id.signUpButton);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    public void LoginActivity() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void register(View view) {
        signUpFunction();
    }

    public void sendVerificationEmail() {
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Register Successfully,please verify your email !!", Toast.LENGTH_SHORT).show();
                    email.setText("");
                    password.setText("");
                    LoginActivity();
                }

            }
        });

    }

    public void signUpFunction() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendVerificationEmail();

                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {

                            Log.e("Authentication",task.getException().toString());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

}