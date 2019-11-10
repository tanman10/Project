package com.example.walkinclinic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NavigationDrawer extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private String currentUserId;
    private TextView introText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        navigationView = (NavigationView) findViewById((R.id.navigationView));
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(NavigationDrawer.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelector(menuItem);
                return false;
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();



        introText2 = findViewById(R.id.introtext2);

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

                        introText2.setText("Acount owner name: " + person.getFirstName() + " " + person.getLastName()
                                + "\nWelcome " + person.getFirstName() + "! You are logged in as " + person.getRole() + ".");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void UserMenuSelector(MenuItem item){
        switch(item.getItemId()){
            case R.id.addOrDeleteService:
                startActivity(new Intent(NavigationDrawer.this, AdminProfileActivity.class));
                break;

            case R.id.deleteUser:
                startActivity(new Intent(NavigationDrawer.this, practice.class));
                break;
        }
    }

}
