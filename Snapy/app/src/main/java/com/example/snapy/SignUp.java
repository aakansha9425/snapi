package com.example.snapy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity{

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    TextView tvSignup;
    EditText etnameReg, etemailReg, etDobReg, etPasswrdReg;
    Button btnSignup;
    RadioButton rbmaleReg, rbfemaleReg, rbotherReg;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tvSignup = findViewById(R.id.tvSignUp);
        etnameReg = findViewById(R.id.etnameregister);
        etemailReg = findViewById(R.id.etEmailregister);
        etDobReg = findViewById(R.id.etDobregister);
        etPasswrdReg = findViewById(R.id.etpasswrdregister);
        rbmaleReg = findViewById(R.id.rbmaleregister);
        rbfemaleReg = findViewById(R.id.rbfemaleregister);
        rbotherReg = findViewById(R.id.rbOtherregister);
        btnSignup = findViewById(R.id.btnSignup);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    public void registerUser(View v) {
        String fullName = etnameReg.getText().toString().trim();
        String password = etPasswrdReg.getText().toString().trim();
        String dob = etDobReg.getText().toString().trim();
        String email = etemailReg.getText().toString().trim();

        if (fullName.isEmpty()) {
            etnameReg.setError("Full Name is required");
            etnameReg.requestFocus();
            return;
        }

        if(dob.isEmpty()){
            etDobReg.setError("Date of Birth is required");
            etDobReg.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etemailReg.setError("Email is required");
            etemailReg.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemailReg.setError("Please provide valid email");
            etemailReg.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPasswrdReg.setError("Password is required");
            etPasswrdReg.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPasswrdReg.setError("Min password length must be 6 characters");
            etPasswrdReg.requestFocus();
            return;
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()){
            etPasswrdReg.setError("Password too weak");
            etPasswrdReg.requestFocus();
            return;
        }

        signUpFunction();
    }

   /* private boolean validateEmail() {
        String emailInput = etemailReg.getText().toString().trim();
        if (emailInput.isEmpty()) {
            etemailReg.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etemailReg.setError("Please enter a valid email address");
            return false;
        } else {
            etemailReg.setError(null);
            return true;
        }
    }*/

    public void LoginActivity() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void sendVerificationEmail() {
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Register Successfully,please verify your email !!", Toast.LENGTH_SHORT).show();
                    etemailReg.setText("");
                    etPasswrdReg.setText("");
                    LoginActivity();
                }

            }
        });

    }

    public void signUpFunction() {
        mAuth.createUserWithEmailAndPassword(etemailReg.getText().toString(), etPasswrdReg.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendVerificationEmail();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {

                            Log.e("Authentication",task.getException().toString());
                            Toast.makeText(getApplicationContext(), "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}