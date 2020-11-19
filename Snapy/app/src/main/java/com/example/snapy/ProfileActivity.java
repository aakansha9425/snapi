package com.example.snapy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.snapy.LoginSignUp.MainActivity;
import com.example.snapy.fragment.CameraFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference refrence;

    private String userID;
    private Button logout;

    ImageView imageView;
    TextView profileName,profileEmail,profiledob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //profileName=findViewById(R.id.profileName);
        //profileEmail=findViewById(R.id.profileDob);
        //profiledob=findViewById(R.id.profileDob);

        logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
        imageView=findViewById(R.id.arrowback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        refrence = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

       // final TextView greetingTextView = findViewById(R.id.greeting);
        final TextView fullNameTextView = findViewById(R.id.profileName);
        final TextView emailTextView = findViewById(R.id.profileEmail);
        final TextView dobTextView = findViewById(R.id.profileDob);

        refrence.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String dob = userProfile.dob;

                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    dobTextView.setText(dob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Something wrong happened!",Toast.LENGTH_LONG).show();

            }
        });



        
    }
}