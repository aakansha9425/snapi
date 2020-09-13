package com.example.snapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    EditText email,password;
    Button login;
    private FirebaseAuth mAuth;
    public void alreadyRegister(View view){
        loginInFunction();
    }
    public void loginInFunction(){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            welcomeActivity();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login=findViewById(R.id.Button);
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextPassword2);
        mAuth = FirebaseAuth.getInstance();
    }
    public void welcomeActivity(){
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
    }
}