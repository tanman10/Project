package com.example.walkinclinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{


    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText email;
    private Person person;
    private String role;
    private Spinner mspinner;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mspinner = (Spinner)findViewById(R.id.roleTextField);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreateAccountActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(myAdapter);


        // assign text input to variable
        email = (EditText) findViewById(R.id.emailTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        firstName = (EditText) findViewById(R.id.firstNameTextField);
        lastName = (EditText) findViewById(R.id.lastNameTextField);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.loginHereText).setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }


    private void registerUser()throws NoSuchAlgorithmException{


        myRef = FirebaseDatabase.getInstance().getReference().child("Persons");


        final String email1 = email.getText().toString().trim();
        final String password1 = buildHash(password.getText().toString().trim());
        final String firstName1 = firstName.getText().toString().trim();
        final String lastName1 = lastName.getText().toString().trim();
        final String role1 = mspinner.getSelectedItem().toString();



        // minimum conditions for creating an account
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

        if (firstName1.isEmpty()) {
            firstName.setError("First name is required");
            firstName.requestFocus();
            return;
        }

        if (lastName1.isEmpty()) {
            lastName.setError("Last name is required");
            lastName.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    final String id = mAuth.getUid();
                    final Person person = new Person(firstName1, lastName1, password1, email1, role1, id);
                    mDatabase.getReference("Persons")           // this sets the name of the child in the database
                            .child(mAuth.getUid()).setValue(person)     // this write to the database
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        finish();
                                        Toast.makeText(CreateAccountActivity.this, "Account created", Toast.LENGTH_LONG).show();
                                        // after successful login and write go to next activity
                                        startActivity(new Intent(CreateAccountActivity.this, ProfileActivity.class));
                                    } else{

                                    }
                                }
                            });
                } else {

                }
            }
        });

    }

    private String buildHash(String input) throws NoSuchAlgorithmException{


            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.US_ASCII));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

    }


    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.signUpButton:
                try {
                    registerUser();
                } catch (NoSuchAlgorithmException csne){
                    throw new java.lang.IllegalStateException("Unexpected value: " + view.getId());
                }
                break;

            case R.id.loginHereText:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
