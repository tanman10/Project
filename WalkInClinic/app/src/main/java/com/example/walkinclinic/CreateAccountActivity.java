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


    private EditText signUpfirstName;
    private EditText signUplastName;
    private EditText signUppassword;
    private EditText signUpemail;
    private Spinner signUpmspinner;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signUpmspinner = (Spinner)findViewById(R.id.signUproleTextField);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CreateAccountActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUpmspinner.setAdapter(myAdapter);


        // assign text input to variable
        signUpemail = (EditText) findViewById(R.id.signUpemailTextField);
        signUppassword = (EditText) findViewById(R.id.signUppasswordTextField);
        signUpfirstName = (EditText) findViewById(R.id.signUpfirstNameTextField);
        signUplastName = (EditText) findViewById(R.id.signUplastNameTextField);


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

        final String Createemail = signUpemail.getText().toString().trim();
        final String Createpassword = buildHash(signUppassword.getText().toString().trim());
        final String CreatefirstName = signUpfirstName.getText().toString().trim();
        final String CreatelastName = signUplastName.getText().toString().trim();
        final String Createrole = signUpmspinner.getSelectedItem().toString();

        // minimum conditions for creating an account
        if (Createemail.isEmpty()) {
            signUpemail.setError("Email is required");
            signUpemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Createemail).matches()) {
            signUpemail.setError("Please enter a valid email");
            signUpemail.requestFocus();
            return;
        }

        if (Createpassword.isEmpty()) {
            signUppassword.setError("Password is required");
            signUppassword.requestFocus();
            return;
        }

        if (Createpassword.length() < 6) {
            signUppassword.setError("Minimum length of password should be 6");
            signUppassword.requestFocus();
            return;
        }

        if (CreatefirstName.isEmpty()) {
            signUpfirstName.setError("First name is required");
            signUpfirstName.requestFocus();
            return;
        }

        if (CreatelastName.isEmpty()) {
            signUplastName.setError("Last name is required");
            signUplastName.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Createemail, Createpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    final String id = mAuth.getUid();
                    final Person person = new Person(CreatefirstName, CreatelastName, Createpassword, Createemail, Createrole, id);
                        mDatabase.getReference("Persons").child(mAuth.getUid()).setValue(person);
                                            if(Createrole.equals("Employee")) {
                                                finish();
                                                Toast.makeText(CreateAccountActivity.this, "Account created", Toast.LENGTH_LONG).show();
                                                // after successful login and write go to next activity
                                                startActivity(new Intent(CreateAccountActivity.this, createClinic.class));
                                            } else {
                                                finish();
                                                Toast.makeText(CreateAccountActivity.this, "Account created", Toast.LENGTH_LONG).show();
                                                // after successful login and write go to next activity
                                                startActivity(new Intent(CreateAccountActivity.this, ProfileActivity.class));
                                            }

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
