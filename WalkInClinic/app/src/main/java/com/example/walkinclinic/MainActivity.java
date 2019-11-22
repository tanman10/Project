package com.example.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //create this variable
    FirebaseAuth loginmAuth;

    // create the space to save the inputted text
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize this
        loginmAuth = FirebaseAuth.getInstance();

        // Initialize the space with the inputted text as type EditText
        email = (EditText) findViewById(R.id.loginemailTextField);
        password = (EditText) findViewById(R.id.loginpasswordTextField);

        // initialize buttons/clickable field to click
        findViewById(R.id.createAccountText).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);
    }


    // send fields to database
    private void Login() throws NoSuchAlgorithmException{
        // convert the initialized space from type EditText to String
        String loginemail = email.getText().toString().trim();
        String loginpassword = buildHash(password.getText().toString().trim());

        // validation fields
        if (loginemail.isEmpty()) {

            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }
        if (loginpassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (loginpassword.length() < 6) {
            password.setError("Minimum length of password should be 6");
            password.requestFocus();
            return;
        }

        // predefined method for login
        loginmAuth.signInWithEmailAndPassword(loginemail, loginpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    final FirebaseUser mUser = loginmAuth.getCurrentUser();
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

                                    }else if (person.getRole().equals("Employee")){
                                        finish();
                                        Intent intent = new Intent(MainActivity.this, createClinic.class);
                                        //clear the top of the stack
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    } else{
                                        finish();
                                        Intent intent = new Intent(MainActivity.this, ProfilePatientActivity.class);
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


    private String buildHash(String input) throws NoSuchAlgorithmException {


        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.US_ASCII));

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }



    // Just open the activity page if still logged in
    @Override
    protected void onStart() {
        super.onStart();
        if (loginmAuth.getCurrentUser() != null) {
            final FirebaseUser mUser = loginmAuth.getCurrentUser();
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

                        }else if (person.getRole().equals("Employee")){
                            finish();
                            Intent intent = new Intent(MainActivity.this, createClinic.class);
                            //clear the top of the stack
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else{
                            finish();
                            Intent intent = new Intent(MainActivity.this, ProfilePatientActivity.class);
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

    }


    // tell the buttons/clickable fields what they do when clicked
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.createAccountText:
                finish();
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;

            case R.id.loginButton:
                try {
                    Login();
                } catch (NoSuchAlgorithmException csne){
                    throw new java.lang.IllegalStateException("Unexpected value: " + view.getId());
                }
                break;
        }
    }

}
