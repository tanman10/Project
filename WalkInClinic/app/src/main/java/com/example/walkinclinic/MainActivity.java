package com.example.walkinclinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //create this variable
    FirebaseAuth mAuth;

    // create the space to save the inputted text
    EditText email;
    EditText password;

    private FirebaseUser mUser;
    private DatabaseReference myRef;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize this
        mAuth = FirebaseAuth.getInstance();

        // Initialize the space with the inputted text as type EditText
        email = (EditText) findViewById(R.id.emailTextField);
        password = (EditText) findViewById(R.id.passwordTextField);


        // initialize buttons/clickable field to click
        findViewById(R.id.createAccountText).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);
    }


    // send fields to database
    private void Login() {
        // convert the initialized space from type EditText to String
        final String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        // validation fields
        if (email1.isEmpty()) {

            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }
        if (password1.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (password1.length() < 6) {
            password.setError("Minimum length of password should be 6");
            password.requestFocus();
            return;
        }

        // predefined method for login
        mAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    final FirebaseUser mUser = mAuth.getCurrentUser();
                    if(mUser == null){
                        finish();
                    } else {
                        final String currentUserId = mUser.getUid();
                        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                        // connect the space with the intermediary : activity <-> space <-> Database
                        myRef.child("Persons").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {

                                    // tell the database and activity which class they're working with
                                    Person person = dataSnapshot.getValue(Person.class);

                                    if(person.getEmail().equals("fakeemail@gmail.com")){
                                        finish();
                                        Intent intent = new Intent(MainActivity.this, NavigationDrawer.class);
                                        //clear the top of the stack
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }else{
                                        finish();
                                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

                                        //clear the top of the stack
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Just open the activity page if still logged in
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            // need to change this to send user to the correct profile activity
            //
            //

            ///

            //
            //
            //
            //
            //
            //
            //
            //
            //
            //
            //
            //
            ///
            //

            //
            //

            ///
            //
            //
        }

    }


    // tell the buttons/clickable fields what they do when clicked
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccountText:
                finish();
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;

            case R.id.loginButton:
                Login();
                break;
        }
    }

}
