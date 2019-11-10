package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class AdminProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    EditText editTextName;
    EditText editTextPrice;
    Button buttonAddProduct;
    ListView listViewProducts;

    private FirebaseAuth mAuth;
    private Spinner mspinner;
    List<Service> services;
    DatabaseReference databaseProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        // Initialize these
        mAuth = FirebaseAuth.getInstance();

        // Intialize button/clickable field to click
        findViewById(R.id.signOutButton).setOnClickListener(this);

        databaseProducts = FirebaseDatabase.getInstance().getReference("products");

        mspinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AdminProfileActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names2));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(myAdapter);

        editTextName = (EditText) findViewById(R.id.editTextName);
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        buttonAddProduct = (Button) findViewById(R.id.addButton);

        services = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service product = services.get(i);
                showUpdateDeleteDialog(product.getId(), product.getServicename());
                return true;
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseProducts.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                services.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service product = postSnapshot.getValue(Service.class);
                    services.add(product);
                }
                ServiceList productsAdapter = new ServiceList(AdminProfileActivity.this, services);
                listViewProducts.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    private void showUpdateDeleteDialog(final String serviceId, String serviceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_service, null);
        dialogBuilder.setView(dialogView);


        final Spinner mspinner2;
        mspinner2 = (Spinner)dialogView.findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names2));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner2.setAdapter(myAdapter2);


        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = mspinner2.getSelectedItem().toString();;
                if (!TextUtils.isEmpty(name)) {
                    updateProduct(serviceId, name, role);
                    b.dismiss();
                }else{
                    Toast.makeText(AdminProfileActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(serviceId);
                b.dismiss();
            }
        });
    }

    private void updateProduct(String id, String name, String role) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
        Service product = new Service(id, name, role);
        dR.setValue(product);
        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteProduct(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    private void addProduct() {
        String name = editTextName.getText().toString().trim();
        String role = mspinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            String id = databaseProducts.push().getKey();
            Service product = new Service(id, name, role);
            databaseProducts.child(id).setValue(product);
            editTextName.setText("");
            Toast.makeText(this, "Service added", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }



    // tells the buttons/clickable field what they do when pressed
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signOutButton:
                mAuth.signOut();
                finish();
                startActivity(new Intent(AdminProfileActivity.this, MainActivity.class));
                break;

        }
    }



}
