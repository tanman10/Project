package com.example.walkinclinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private String currentUserId;

    // create intermediary space : activity - space - Database
    private TextView fullName;
    private TextView introText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Intialize button/clickable field to click
        findViewById(R.id.signOutButton).setOnClickListener(this);

        // Initialize these
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        // Initialize the space to activity : activity <-> space - Database
        fullName = findViewById(R.id.fullNameField);
        introText = findViewById(R.id.IntroductionText);

        //MUST check is User is on
        // idky fully but basically without the if statement the line currentUserId = mUser.getUid
        // doesn't work

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            currentUserId = mUser.getUid();

            // connect the space with the intermediary : activity <-> space <-> Database
            myRef.child("Persons").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        // tell the database and activity which class they're working with
                        Person person = dataSnapshot.getValue(Person.class);

                        // read the data from the database to the activity : activity <--(data)-- Database
                        fullName.setText("Acount owner name: " + person.getFirstName() + " " + person.getLastName());
                        introText.setText("Welcome " + person.getFirstName() + "! You are logged in as " + person.getRole() + ".");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }



    // tells the buttons/clickable field what they do when pressed
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signOutButton:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }

}
