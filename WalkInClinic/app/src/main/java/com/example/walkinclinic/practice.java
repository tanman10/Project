package com.example.walkinclinic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Intent;
import android.util.Patterns;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import java.util.ArrayList;
import java.util.List;

public class practice extends AppCompatActivity {

    ListView listViewPerson;
    List<Person> persons;
    DatabaseReference databasePerson;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        text = (EditText) findViewById(R.id.editTextDelay);

        databasePerson = FirebaseDatabase.getInstance().getReference("Persons");

        listViewPerson = (ListView) findViewById(R.id.listViewPerson);

        persons = new ArrayList<>();

        listViewPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person product = persons.get(i);
                showUpdateDeleteDialog(product.getId());
                return true;
            }
        });
    }

    private void showUpdateDeleteDialog(final String productId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_person, null);
        dialogBuilder.setView(dialogView);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(productId + "check");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(productId);
                b.dismiss();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        databasePerson.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                persons.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Person product = postSnapshot.getValue(Person.class);
                    persons.add(product);
                }
                PersonList productsAdapter = new PersonList(practice.this, persons);
                listViewPerson.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    private boolean deleteProduct(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Persons").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
