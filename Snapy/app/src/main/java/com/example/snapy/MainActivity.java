package com.example.snapy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button signIn;
    Button logIn;
    public void signInClicked(View view){
        Intent signIn=new Intent(this,SignUp.class);
        startActivity(signIn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn=findViewById(R.id.signUp);
        logIn=findViewById(R.id.logIn);

    }
}
