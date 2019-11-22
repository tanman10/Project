package com.example.walkinclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class createClinic extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private EditText clinicName;
    private EditText clinicAddress;
    private EditText clinicPhoneNum;
    private Spinner mspinner;
    private Spinner mspinner2;

    private Spinner startHM1;
    private Spinner startMM1;
    private Spinner endHM1;
    private Spinner endMM1;

    private Spinner startHTU1;
    private Spinner startMTU1;
    private Spinner endHTU1;
    private Spinner endMTU1;

    private Spinner startHW1;
    private Spinner startMW1;
    private Spinner endHW1;
    private Spinner endMW1;

    private Spinner startHTH1;
    private Spinner startMTH1;
    private Spinner endHTH1;
    private Spinner endMTH1;

    private Spinner startHF1;
    private Spinner startMF1;
    private Spinner endHF1;
    private Spinner endMF1;

    private Spinner startHSA1;
    private Spinner startMSA1;
    private Spinner endHSA1;
    private Spinner endMSA1;

    private Spinner startHSU1;
    private Spinner startMSU1;
    private Spinner endHSU1;
    private Spinner endMSU1;

    private TextView servicesAddedtextView;
    private TextView addServicetextView;
    private TextView CreateClinicText;
    private TextView MondayStartTime;
    private TextView TuesdayStartTime;
    private TextView WednesdayStartTime;
    private TextView ThursdayStartTime;
    private TextView FridayStartTime;
    private TextView SaturdayStartTime;
    private TextView SundayStartTime;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    private String id;
    List<Service> services;
    List<Service> services2;
    ListView addAService;
    ListView servicesAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_clinic);

        CreateClinicText = findViewById(R.id.CreateClinicText);
        servicesAddedtextView = findViewById(R.id.servicesAddedtextView);
        addServicetextView = findViewById(R.id.addServicetextView);

        CreateClinicText.setText("You have registered as an Employee, please provide your clinics information");
        addServicetextView.setText("Hold down to select the services you want");
        servicesAddedtextView.setText("Services added will be displayed here");

        MondayStartTime = findViewById(R.id.MondayStartTime);
        TuesdayStartTime = findViewById(R.id.TuesdayStartTime);
        WednesdayStartTime = findViewById(R.id.WednesdayStartTime);
        ThursdayStartTime = findViewById(R.id.ThursdayStartTime);
        FridayStartTime = findViewById(R.id.FridayStartTime);
        SaturdayStartTime = findViewById(R.id.SaturdayStartTime);
        SundayStartTime = findViewById(R.id.SundayStartTime);

        MondayStartTime.setText("Monday starting hours and ending hours");
        TuesdayStartTime.setText("Tuesday starting hours and ending hours");
        WednesdayStartTime.setText("Wednesday starting hours and ending hours");
        ThursdayStartTime.setText("Thursday starting hours and ending hours");
        FridayStartTime.setText("Friday starting hours and ending hours");
        SaturdayStartTime.setText("Saturday starting hours and ending hours");
        SundayStartTime.setText("Sunday starting hours and ending hours");

        mspinner = (Spinner)findViewById(R.id.clinicPayment);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names3));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner.setAdapter(myAdapter);

        mspinner2 = (Spinner)findViewById(R.id.clinicInsurance);
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names4));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mspinner2.setAdapter(myAdapter2);

        // assign text input to variable
        clinicName = (EditText) findViewById(R.id.clinicName);
        clinicAddress = (EditText) findViewById(R.id.clinicAddress);
        clinicPhoneNum = (EditText) findViewById(R.id.clinicPhoneNum);

        mAuth = FirebaseAuth.getInstance();
        id = mAuth.getCurrentUser().getUid();

        myRef = FirebaseDatabase.getInstance().getReference("clinic");
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("productsEmployee").child(id);
        databaseReference3 = FirebaseDatabase.getInstance().getReference("times").child(id);

        addAService = (ListView) findViewById(R.id.addAService);
        servicesAdded = (ListView) findViewById(R.id.servicesAdded);

        services = new ArrayList<>();
        services2 = new ArrayList<>();

        findViewById(R.id.createClinic).setOnClickListener(this);

        addAService.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service product = services.get(i);
                addServiceToClinic(product.getId(), product.getServicename(), product.getRole());
                Toast.makeText(getApplicationContext(), "Service Added", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        servicesAdded.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service product = services2.get(i);
                removeService(product.getId());
                Toast.makeText(getApplicationContext(), "Service Removed", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        startHM1 = (Spinner)findViewById(R.id.startHM1);
        ArrayAdapter<String> myAdapterstartHM1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterstartHM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHM1.setAdapter(myAdapterstartHM1);

        startMM1 = (Spinner)findViewById(R.id.startMM1);
        ArrayAdapter<String> myAdapterstartMM1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterstartMM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMM1.setAdapter(myAdapterstartMM1);

        endHM1 = (Spinner)findViewById(R.id.endHM1);
        ArrayAdapter<String> myAdapterendHM1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterendHM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHM1.setAdapter(myAdapterendHM1);

        endMM1 = (Spinner)findViewById(R.id.endMM1);
        ArrayAdapter<String> myAdapterendMM1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterendMM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMM1.setAdapter(myAdapterendMM1);



        startHTU1 = (Spinner)findViewById(R.id.startHTU1);
        ArrayAdapter<String> myAdapterstartHTU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterstartHTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHTU1.setAdapter(myAdapterstartHTU1);

        startMTU1 = (Spinner)findViewById(R.id.startMTU1);
        ArrayAdapter<String> myAdapterstartMTU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterstartMTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMTU1.setAdapter(myAdapterstartMTU1);

        endHTU1 = (Spinner)findViewById(R.id.endHTU1);
        ArrayAdapter<String> myAdapterendHTU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterendHTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHTU1.setAdapter(myAdapterendHTU1);

        endMTU1 = (Spinner)findViewById(R.id.endMTU1);
        ArrayAdapter<String> myAdapterendMTU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterendMTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMTU1.setAdapter(myAdapterendMTU1);




        startHW1 = (Spinner)findViewById(R.id.startHW1);
        ArrayAdapter<String> myAdapterstartHW1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterstartHW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHW1.setAdapter(myAdapterstartHW1);

        startMW1 = (Spinner)findViewById(R.id.startMW1);
        ArrayAdapter<String> myAdapterstartMW1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterstartMW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMW1.setAdapter(myAdapterstartMW1);

        endHW1 = (Spinner)findViewById(R.id.endHW1);
        ArrayAdapter<String> myAdapterendHW1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterendHW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHW1.setAdapter(myAdapterendHW1);

        endMW1 = (Spinner)findViewById(R.id.endMW1);
        ArrayAdapter<String> myAdapterendMW1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterendMW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMW1.setAdapter(myAdapterendMW1);







        startHTH1 = (Spinner)findViewById(R.id.startHTH1);
        ArrayAdapter<String> myAdapterstartHTH1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterstartHTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHTH1.setAdapter(myAdapterstartHTH1);

        startMTH1 = (Spinner)findViewById(R.id.startMTH1);
        ArrayAdapter<String> myAdapterstartMTH1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterstartMTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMTH1.setAdapter(myAdapterstartMTH1);

        endHTH1 = (Spinner)findViewById(R.id.endHTH1);
        ArrayAdapter<String> myAdapterendHTH1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterendHTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHTH1.setAdapter(myAdapterendHTH1);

        endMTH1 = (Spinner)findViewById(R.id.endMTH1);
        ArrayAdapter<String> myAdapterendMTH1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterendMTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMTH1.setAdapter(myAdapterendMTH1);





        startHF1 = (Spinner)findViewById(R.id.startHF1);
        ArrayAdapter<String> myAdapterstartHF1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterstartHF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHF1.setAdapter(myAdapterstartHF1);

        startMF1 = (Spinner)findViewById(R.id.startMF1);
        ArrayAdapter<String> myAdapterstartMF1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterstartMM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMF1.setAdapter(myAdapterstartMF1);

        endHF1 = (Spinner)findViewById(R.id.endHF1);
        ArrayAdapter<String> myAdapterendHF1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterendHF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHF1.setAdapter(myAdapterendHF1);

        endMF1 = (Spinner)findViewById(R.id.endMF1);
        ArrayAdapter<String> myAdapterendMF1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterendMF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMF1.setAdapter(myAdapterendMF1);




        startHSA1 = (Spinner)findViewById(R.id.startHSA1);
        ArrayAdapter<String> myAdapterstartHSA1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterstartHSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHSA1.setAdapter(myAdapterstartHSA1);

        startMSA1 = (Spinner)findViewById(R.id.startMSA1);
        ArrayAdapter<String> myAdapterstartMSA1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterstartMSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMSA1.setAdapter(myAdapterstartMSA1);

        endHSA1 = (Spinner)findViewById(R.id.endHSA1);
        ArrayAdapter<String> myAdapterendHSA1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterendHSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHSA1.setAdapter(myAdapterendHSA1);

        endMSA1 = (Spinner)findViewById(R.id.endMSA1);
        ArrayAdapter<String> myAdapterendMSA1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterendMSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMSA1.setAdapter(myAdapterendMSA1);





        startHSU1 = (Spinner)findViewById(R.id.startHSU1);
        ArrayAdapter<String> myAdapterstartHSU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterstartHSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHSU1.setAdapter(myAdapterstartHSU1);

        startMSU1 = (Spinner)findViewById(R.id.startMSU1);
        ArrayAdapter<String> myAdapterstartMSU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterstartMSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startMSU1.setAdapter(myAdapterstartMSU1);

        endHSU1 = (Spinner)findViewById(R.id.endHSU1);
        ArrayAdapter<String> myAdapterendHSU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterendHSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endHSU1.setAdapter(myAdapterendHSU1);

        endMSU1 = (Spinner)findViewById(R.id.endMSU1);
        ArrayAdapter<String> myAdapterendMSU1 = new ArrayAdapter<String>(createClinic.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterendMSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endMSU1.setAdapter(myAdapterendMSU1);
    }

    private void addServiceToClinic(final String serviceId, String serviceName, String role) {
        Service product = new Service(serviceId, serviceName, role);
        databaseReference2.child(serviceId).setValue(product);
    }

    private void removeService(final String serviceId){
        databaseReference2.child(serviceId).removeValue();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            final FirebaseUser mUser = mAuth.getCurrentUser();
            final String currentUserId = mUser.getUid();
            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

            // connect the space with the intermediary : activity <-> space <-> Database
            myRef.child("clinic").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        finish();
                        startActivity(new Intent(createClinic.this, ProfilePatientActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        databaseReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                services.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service product = postSnapshot.getValue(Service.class);
                    services.add(product);
                }
                ServiceList productsAdapter = new ServiceList(createClinic.this, services);
                addAService.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2){
                services2.clear();
                for(DataSnapshot postSnapshot : dataSnapshot2.getChildren()){
                    Service product2 = postSnapshot.getValue(Service.class);
                    services2.add(product2);
                }
                ServiceList productsAdapter2 = new ServiceList(createClinic.this, services2);
                servicesAdded.setAdapter(productsAdapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.createClinic:

                final String name = clinicName.getText().toString().trim();
                final String address = clinicAddress.getText().toString().trim();
                final String phoneNum = clinicPhoneNum.getText().toString().trim();
                final String payment = mspinner.getSelectedItem().toString();

                final String insurance = mspinner2.getSelectedItem().toString();
                final String startHM1F = startHM1.getSelectedItem().toString();
                final String startMM1F = startMM1.getSelectedItem().toString();
                final String endHM1F = endHM1.getSelectedItem().toString();
                final String endMM1F = endMM1.getSelectedItem().toString();

                final String startHTU1F = startHTU1.getSelectedItem().toString();
                final String startMTU1F = startMTU1.getSelectedItem().toString();
                final String endHTU1F = endHTU1.getSelectedItem().toString();
                final String endMTU1F = endMTU1.getSelectedItem().toString();

                final String startHW1F = startHW1.getSelectedItem().toString();
                final String startMW1F = startMW1.getSelectedItem().toString();
                final String endHW1F = endHW1.getSelectedItem().toString();
                final String endMW1F = endMW1.getSelectedItem().toString();

                final String startHTH1F = startHTH1.getSelectedItem().toString();
                final String startMTH1F = startMTH1.getSelectedItem().toString();
                final String endHTH1F = endHTH1.getSelectedItem().toString();
                final String endMTH1F = endMTH1.getSelectedItem().toString();

                final String startHF1F = startHF1.getSelectedItem().toString();
                final String startMF1F = startMF1.getSelectedItem().toString();
                final String endHF1F = endHF1.getSelectedItem().toString();
                final String endMF1F = endMF1.getSelectedItem().toString();

                final String startHSA1F = startHSA1.getSelectedItem().toString();
                final String startMSA1F = startMSA1.getSelectedItem().toString();
                final String endHSA1F = endHSA1.getSelectedItem().toString();
                final String endMSA1F = endMSA1.getSelectedItem().toString();

                final String startHSU1F = startHSU1.getSelectedItem().toString();
                final String startMSU1F = startMSU1.getSelectedItem().toString();
                final String endHSU1F = endHSU1.getSelectedItem().toString();
                final String endMSU1F = endMSU1.getSelectedItem().toString();


                final String startM = startHM1F + ":" + startMM1F;
                final String endM = endHM1F + ":" + endMM1F;

                final String startTU = startHTU1F + ":" + startMTU1F;
                final String endTU = endHTU1F + ":" + endMTU1F;

                final String startW = startHW1F + ":" + startMW1F;
                final String endW = endHW1F + ":" + endMW1F;

                final String startTH = startHTH1F + ":" + startMTH1F;
                final String endTH = endHTH1F + ":" + endMTH1F;

                final String startF = startHF1F + ":" + startMF1F;
                final String endF = endHF1F + ":" + endMF1F;

                final String startSA = startHSA1F + ":" + startMSA1F;
                final String endSA = endHSA1F + ":" + endMSA1F;

                final String startSU = startHSU1F + ":" + startMSU1F;
                final String endSU = endHSU1F + ":" + endMSU1F;


                String clinicName1 = clinicName.getText().toString().trim();
                String clinicAddress1 = clinicAddress.getText().toString().trim();
                String clinicPhoneNum1 = clinicPhoneNum.getText().toString().trim();

                if (clinicName1.isEmpty()) {
                    clinicName.setError("Name is required");
                    clinicName.requestFocus();
                    return;
                }

                if (clinicAddress1.isEmpty()) {
                    clinicAddress.setError("Address is required");
                    clinicAddress.requestFocus();
                    return;
                }

                if (clinicPhoneNum1.length() != 10) {
                    clinicPhoneNum.setError("Only 10 digit phone numbers are accepted");
                    clinicPhoneNum.requestFocus();
                    return;
                }

                try {
                    int num = Integer.parseInt(clinicPhoneNum1);
                }catch(NumberFormatException e){
                    clinicPhoneNum.setError("please enter a valid phone number");
                    clinicPhoneNum.requestFocus();
                }


                Time time = new Time(startM, endM, startTU, endTU, startW, endW, startTH, endTH, startF, endF, startSA, endSA, startSU, endSU);
                databaseReference3.setValue(time);

                Clinic clinic = new Clinic(name, address, phoneNum, insurance, payment);
                myRef.child(id).setValue(clinic);
                Toast.makeText(getApplicationContext(), "Clinic created", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, ProfilePatientActivity.class));
                break;

        }
    }

}
