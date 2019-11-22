package com.example.walkinclinic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ProfilePatientActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout drawerLayout2;
    private Toolbar toolbar2;
    private ActionBarDrawerToggle actionBarDrawerToggle2;
    private NavigationView navigationView2;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private String currentUserId;
    private TextView textView;

    private TextView TheListOfServices;
    private TextView YourServices;

    private TextView name2;
    private TextView address2;
    private TextView phoneNumber2;
    private TextView insuranceType2;
    private TextView paymentMethod2;
    private TextView workingHours;

    private TextView NewMondayTime;
    private TextView NewTuesdayTime;
    private TextView NewWednesdayTime;
    private TextView NewThursdayTime;
    private TextView NewFridayTime;
    private TextView NewSaturdayTime;
    private TextView NewSundayTime;


    private EditText name;
    private EditText address;
    private EditText phoneNumber;

    private Spinner Activitymspinner;
    private Spinner Activitymspinner2;

    private Spinner NstartHM1;
    private Spinner NstartMM1;
    private Spinner NendHM1;
    private Spinner NendMM1;

    private Spinner NstartHTU1;
    private Spinner NstartMTU1;
    private Spinner NendHTU1;
    private Spinner NendMTU1;

    private Spinner NstartHW1;
    private Spinner NstartMW1;
    private Spinner NendHW1;
    private Spinner NendMW1;

    private Spinner NstartHTH1;
    private Spinner NstartMTH1;
    private Spinner NendHTH1;
    private Spinner NendMTH1;

    private Spinner NstartHF1;
    private Spinner NstartMF1;
    private Spinner NendHF1;
    private Spinner NendMF1;

    private Spinner NstartHSA1;
    private Spinner NstartMSA1;
    private Spinner NendHSA1;
    private Spinner NendMSA1;

    private Spinner NstartHSU1;
    private Spinner NstartMSU1;
    private Spinner NendHSU1;
    private Spinner NendMSU1;



    DatabaseReference databaseProducts;
    DatabaseReference databaseProducts2;
    DatabaseReference databaseProducts3;
    DatabaseReference databaseProducts4;
    List<Service> services;
    List<Service> services2;
    ListView listViewServices;
    ListView listViewServicesAdded;

    CalendarView calendarView;

    private FirebaseDatabase mDatabase;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_patient);

        name2 = findViewById(R.id.name);
        address2 = findViewById(R.id.address);
        phoneNumber2 = findViewById(R.id.phone_number);
        insuranceType2 = findViewById(R.id.insurance_type);
        paymentMethod2 = findViewById(R.id.payment_method);

        workingHours = findViewById(R.id.workingHours);

        NewMondayTime = findViewById(R.id.NewMondayTime);
        NewTuesdayTime = findViewById(R.id.NewTuesdayTime);
        NewWednesdayTime = findViewById(R.id.NewWednesdayTime);
        NewThursdayTime = findViewById(R.id.NewThursdayTime);
        NewFridayTime = findViewById(R.id.NewFridayTime);
        NewSaturdayTime = findViewById(R.id.NewSaturdayTime);
        NewSundayTime = findViewById(R.id.NewSundayTime);

        NewMondayTime.setText("Redefine Monday starting and ending hours");
        NewTuesdayTime.setText("Redefine Tuesday starting and ending hours");
        NewWednesdayTime.setText("Redefine Wednesday starting and ending hours");
        NewThursdayTime.setText("Redefine Thursday starting and ending hours");
        NewFridayTime.setText("Redefine Friday starting and ending hours");
        NewSaturdayTime.setText("Redefine Saturday starting and ending hours");
        NewSundayTime.setText("Redefine Sunday starting and ending hours");

        TheListOfServices = findViewById(R.id.TheListOfServices);
        YourServices = findViewById(R.id.YourServices);

        TheListOfServices.setText("Hold services you want to add");
        YourServices.setText("Hold services you want to remove");

        TheListOfServices.setVisibility(View.GONE);
        YourServices.setVisibility(View.GONE);

        address = (EditText)findViewById(R.id.addresssync);
        name = (EditText)findViewById(R.id.namesync);
        phoneNumber = (EditText)findViewById(R.id.phonesync);

        address.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        phoneNumber.setVisibility(View.GONE);

        Activitymspinner = (Spinner)findViewById(R.id.paymentsync);
        ArrayAdapter<String> ActivitymyAdapter = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names3));
        ActivitymyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Activitymspinner.setAdapter(ActivitymyAdapter);

        Activitymspinner2 = (Spinner)findViewById(R.id.insurancesync);
        ArrayAdapter<String> ActivitymyAdapter2 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names4));
        ActivitymyAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Activitymspinner2.setAdapter(ActivitymyAdapter2);

        Activitymspinner.setVisibility(View.GONE);
        Activitymspinner2.setVisibility(View.GONE);

        navigationView2 = (NavigationView) findViewById((R.id.navigationView2));

        Menu nav_Menu = navigationView2.getMenu();
        nav_Menu.findItem(R.id.save).setVisible(false);

        toolbar2 = (Toolbar) findViewById(R.id.mainToolbar2);
        drawerLayout2 = (DrawerLayout) findViewById(R.id.drawable_layout2);
        actionBarDrawerToggle2 = new ActionBarDrawerToggle(ProfilePatientActivity.this, drawerLayout2, R.string.drawer_open, R.string.drawer_close);
        drawerLayout2.addDrawerListener(actionBarDrawerToggle2);
        actionBarDrawerToggle2.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        id = mAuth.getCurrentUser().getUid();

        services = new ArrayList<>();
        services2 = new ArrayList<>();

        navigationView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                UserMenuSelector(menuItem);
                return false;
            }
        });

        listViewServices = (ListView) findViewById(R.id.listViewServices);
        listViewServicesAdded = (ListView) findViewById(R.id.listViewServicesAdded);
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");
        databaseProducts2 = FirebaseDatabase.getInstance().getReference("clinic");
        databaseProducts3 = FirebaseDatabase.getInstance().getReference("productsEmployee").child(id);
        databaseProducts4 = FirebaseDatabase.getInstance().getReference("times").child(id);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        listViewServices.setVisibility(View.GONE);

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service product = services.get(i);
                addServiceToClinic(product.getId(), product.getServicename(), product.getRole());
                Toast.makeText(getApplicationContext(), "Service Added", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        listViewServicesAdded.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service product = services2.get(i);
                removeService(product.getId());
                Toast.makeText(getApplicationContext(), "Service Removed", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Toast.makeText(getApplicationContext(), "Date:\nDay = " + dayOfMonth + "\nMonth = "
                  //      + month + "\nYear = " + year, Toast.LENGTH_LONG).show();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                displayHours(dayOfWeek);
            }
        });
        textView = findViewById(R.id.textView);

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            currentUserId = mUser.getUid();

            myRef.child("clinic").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        Clinic clinic = dataSnapshot.getValue(Clinic.class);

                        name2.setText("Clinic name: " + clinic.getName());
                        address2.setText("Clinic address: " + clinic.getAddress());
                        phoneNumber2.setText("Clinic phone number: " + clinic.getPhoneNum());
                        insuranceType2.setText("Clinic insurance type: " + clinic.getInsurance());
                        paymentMethod2.setText("Clinic payment method: " + clinic.getPayment());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            // connect the space with the intermediary : activity <-> space <-> Database
            myRef.child("Persons").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        // tell the database and activity which class they're working with
                        Person person = dataSnapshot.getValue(Person.class);

                        // read the data from the database to the activity : activity <--(data)-- Database

                        textView.setText("Acount owner name: " + person.getFirstName() + " " + person.getLastName()
                                + "\nWelcome " + person.getFirstName() + "! You are logged in as " + person.getRole() + ".");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            myRef.child("times").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Time time = dataSnapshot.getValue(Time.class);
                    workingHours.setText("Sunday hours: " + time.getSUtime() + " - " + time.getSUtime2() + "\n"
                            + "Monday hours: " + time.getMtime() + " - " + time.getMtime2() + "\n"
                            + "Tuesday hours: " + time.getTUtime() + " - " + time.getTUtime2() + "\n"
                            + "Wednesday hours: " + time.getWtime() + " - " + time.getWtime2() + "\n"
                            + "Thursday hours: " + time.getTHtime() + " - " + time.getTHtime2() + "\n"
                            + "Friday hours: " + time.getFtime() + " - " + time.getFtime2() + "\n"
                            + "Sunday hours: " + time.getSAtime() + " - " + time.getSAtime2());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        NstartHM1 = (Spinner)findViewById(R.id.NstartHM);
        ArrayAdapter<String> myAdapterNstartHM1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNstartHM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartHM1.setAdapter(myAdapterNstartHM1);

        NstartMM1 = (Spinner)findViewById(R.id.NstartMM);
        ArrayAdapter<String> myAdapterNstartMM1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNstartMM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartMM1.setAdapter(myAdapterNstartMM1);

        NendHM1 = (Spinner)findViewById(R.id.NendHM);
        ArrayAdapter<String> myAdapterNendHM1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNendHM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendHM1.setAdapter(myAdapterNendHM1);

        NendMM1 = (Spinner)findViewById(R.id.NendMM);
        ArrayAdapter<String> myAdapterNendMM1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNendMM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendMM1.setAdapter(myAdapterNendMM1);




        NstartHTU1 = (Spinner)findViewById(R.id.NstartHTU);
        ArrayAdapter<String> myAdapterNstartHTU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNstartHTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartHTU1.setAdapter(myAdapterNstartHTU1);

        NstartMTU1 = (Spinner)findViewById(R.id.NstartMTU);
        ArrayAdapter<String> myAdapterNstartMTU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNstartMTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartMTU1.setAdapter(myAdapterNstartMTU1);

        NendHTU1 = (Spinner)findViewById(R.id.NendHTU);
        ArrayAdapter<String> myAdapterNendHTU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNendHTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendHTU1.setAdapter(myAdapterNendHTU1);

        NendMTU1 = (Spinner)findViewById(R.id.NendMTU);
        ArrayAdapter<String> myAdapterNendMTU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNendMTU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendMTU1.setAdapter(myAdapterNendMTU1);




        NstartHW1 = (Spinner)findViewById(R.id.NstartHW);
        ArrayAdapter<String> myAdapterNstartHW1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNstartHW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartHW1.setAdapter(myAdapterNstartHW1);

        NstartMW1 = (Spinner)findViewById(R.id.NstartMW);
        ArrayAdapter<String> myAdapterNstartMW1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNstartMW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartMW1.setAdapter(myAdapterNstartMW1);

        NendHW1 = (Spinner)findViewById(R.id.NendHW);
        ArrayAdapter<String> myAdapterNendHW1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNendHW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendHW1.setAdapter(myAdapterNendHW1);

        NendMW1 = (Spinner)findViewById(R.id.NendMW);
        ArrayAdapter<String> myAdapterNendMW1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNendMW1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendMW1.setAdapter(myAdapterNendMW1);





        NstartHTH1 = (Spinner)findViewById(R.id.NstartHTH);
        ArrayAdapter<String> myAdapterNstartHTH1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNstartHTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartHTH1.setAdapter(myAdapterNstartHTH1);

        NstartMTH1 = (Spinner)findViewById(R.id.NstartMTH);
        ArrayAdapter<String> myAdapterNstartMTH1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNstartMTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartMTH1.setAdapter(myAdapterNstartMTH1);

        NendHTH1 = (Spinner)findViewById(R.id.NendHTH);
        ArrayAdapter<String> myAdapterNendHTH1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNendHTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendHTH1.setAdapter(myAdapterNendHTH1);

        NendMTH1 = (Spinner)findViewById(R.id.NendMTH);
        ArrayAdapter<String> myAdapterNendMTH1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNendMTH1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendMTH1.setAdapter(myAdapterNendMTH1);





        NstartHF1 = (Spinner)findViewById(R.id.NstartHF);
        ArrayAdapter<String> myAdapterNstartHF1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNstartHF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartHF1.setAdapter(myAdapterNstartHF1);

        NstartMF1 = (Spinner)findViewById(R.id.NstartMF);
        ArrayAdapter<String> myAdapterNstartMF1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNstartMM1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartMF1.setAdapter(myAdapterNstartMF1);

        NendHF1 = (Spinner)findViewById(R.id.NendHF);
        ArrayAdapter<String> myAdapterNendHF1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNendHF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendHF1.setAdapter(myAdapterNendHF1);

        NendMF1 = (Spinner)findViewById(R.id.NendMF);
        ArrayAdapter<String> myAdapterNendMF1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNendMF1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendMF1.setAdapter(myAdapterNendMF1);




        NstartHSA1 = (Spinner)findViewById(R.id.NstartHSA);
        ArrayAdapter<String> myAdapterNstartHSA1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNstartHSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartHSA1.setAdapter(myAdapterNstartHSA1);

        NstartMSA1 = (Spinner)findViewById(R.id.NstartMSA);
        ArrayAdapter<String> myAdapterNstartMSA1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNstartMSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartMSA1.setAdapter(myAdapterNstartMSA1);

        NendHSA1 = (Spinner)findViewById(R.id.NendHSA);
        ArrayAdapter<String> myAdapterNendHSA1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNendHSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendHSA1.setAdapter(myAdapterNendHSA1);

        NendMSA1 = (Spinner)findViewById(R.id.NendMSA);
        ArrayAdapter<String> myAdapterNendMSA1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNendMSA1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendMSA1.setAdapter(myAdapterNendMSA1);





        NstartHSU1 = (Spinner)findViewById(R.id.NstartHSU);
        ArrayAdapter<String> myAdapterNstartHSU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNstartHSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartHSU1.setAdapter(myAdapterNstartHSU1);

        NstartMSU1 = (Spinner)findViewById(R.id.NstartMSU);
        ArrayAdapter<String> myAdapterNstartMSU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNstartMSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NstartMSU1.setAdapter(myAdapterNstartMSU1);

        NendHSU1 = (Spinner)findViewById(R.id.NendHSU);
        ArrayAdapter<String> myAdapterNendHSU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names8));
        myAdapterNendHSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendHSU1.setAdapter(myAdapterNendHSU1);

        NendMSU1 = (Spinner)findViewById(R.id.NendMSU);
        ArrayAdapter<String> myAdapterNendMSU1 = new ArrayAdapter<String>(ProfilePatientActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names7));
        myAdapterNendMSU1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NendMSU1.setAdapter(myAdapterNendMSU1);


        NstartHM1.setVisibility(View.GONE);
        NstartMM1.setVisibility(View.GONE);
        NendHM1.setVisibility(View.GONE);
        NendMM1.setVisibility(View.GONE);
        NstartHTU1.setVisibility(View.GONE);
        NstartMTU1.setVisibility(View.GONE);
        NendHTU1.setVisibility(View.GONE);
        NendMTU1.setVisibility(View.GONE);
        NstartHW1.setVisibility(View.GONE);
        NstartMW1.setVisibility(View.GONE);
        NendHW1.setVisibility(View.GONE);
        NendMW1.setVisibility(View.GONE);
        NstartHTH1.setVisibility(View.GONE);
        NstartMTH1.setVisibility(View.GONE);
        NendHTH1.setVisibility(View.GONE);
        NendMTH1.setVisibility(View.GONE);
        NstartHF1.setVisibility(View.GONE);
        NstartMF1.setVisibility(View.GONE);
        NendHF1.setVisibility(View.GONE);
        NendMF1.setVisibility(View.GONE);
        NstartHSA1.setVisibility(View.GONE);
        NstartMSA1.setVisibility(View.GONE);
        NendHSA1.setVisibility(View.GONE);
        NendMSA1.setVisibility(View.GONE);
        NstartHSU1.setVisibility(View.GONE);
        NstartMSU1.setVisibility(View.GONE);
        NendHSU1.setVisibility(View.GONE);
        NendMSU1.setVisibility(View.GONE);

        NewMondayTime.setVisibility(View.GONE);
        NewTuesdayTime.setVisibility(View.GONE);
        NewWednesdayTime.setVisibility(View.GONE);
        NewThursdayTime.setVisibility(View.GONE);
        NewFridayTime.setVisibility(View.GONE);
        NewSaturdayTime.setVisibility(View.GONE);
        NewSundayTime.setVisibility(View.GONE);

    }

    public void displayHours(int num){
        switch (num) {
            case 1:
                databaseProducts4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Time time = dataSnapshot.getValue(Time.class);
                            Toast.makeText(getApplicationContext(), "Starting time: " + time.getMtime()
                                    + "\nEnding time: " + time.getMtime2(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case 2:
                databaseProducts4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Time time = dataSnapshot.getValue(Time.class);
                            Toast.makeText(getApplicationContext(), "Starting time: " + time.getTUtime()
                                    + "\nEnding time: " + time.getTUtime2(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case 3:
                databaseProducts4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Time time = dataSnapshot.getValue(Time.class);
                            Toast.makeText(getApplicationContext(), "Starting time: " + time.getWtime()
                                    + "\nEnding time: " + time.getWtime2(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case 4:
                databaseProducts4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Time time = dataSnapshot.getValue(Time.class);
                            Toast.makeText(getApplicationContext(), "Starting time: " + time.getTHtime()
                                    + "\nEnding time: " + time.getTHtime2(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case 5:
                databaseProducts4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Time time = dataSnapshot.getValue(Time.class);
                            Toast.makeText(getApplicationContext(), "Starting time: " + time.getFtime()
                                    + "\nEnding time: " + time.getFtime2(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case 6:
                databaseProducts4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Time time = dataSnapshot.getValue(Time.class);
                            Toast.makeText(getApplicationContext(), "Starting time: " + time.getSAtime()
                                    + "\nEnding time: " + time.getSAtime2(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case 7:
                databaseProducts4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Time time = dataSnapshot.getValue(Time.class);
                            Toast.makeText(getApplicationContext(), "Starting time: " + time.getSUtime()
                                    + "\nEnding time: " + time.getSUtime2(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
        }
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
                ServiceList productsAdapter = new ServiceList(ProfilePatientActivity.this, services);
                listViewServices.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

        databaseProducts3.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2){
                services2.clear();
                for(DataSnapshot postSnapshot : dataSnapshot2.getChildren()){
                    Service product2 = postSnapshot.getValue(Service.class);
                    services2.add(product2);
                }
                ServiceList productsAdapter2 = new ServiceList(ProfilePatientActivity.this, services2);
                listViewServicesAdded.setAdapter(productsAdapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }


    private void addServiceToClinic(final String serviceId, String serviceName, String role) {
        Service product = new Service(serviceId, serviceName, role);
        databaseProducts3.child(serviceId).setValue(product);
    }

    private void removeService(final String serviceId){
        databaseProducts3.child(serviceId).removeValue();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle2.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.addButton:
                showAddDialogBox();
                break;
        }
    }


    private void showAddDialogBox() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_service_add, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void save(){

    }


    private void UserMenuSelector(MenuItem item){
        switch(item.getItemId()){
            case R.id.edit:
                navigationView2 = (NavigationView) findViewById((R.id.navigationView2));
                Menu nav_Menu = navigationView2.getMenu();
                nav_Menu.findItem(R.id.save).setVisible(true);
                nav_Menu.findItem(R.id.edit).setVisible(false);

                address.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                phoneNumber.setVisibility(View.VISIBLE);
                Activitymspinner.setVisibility(View.VISIBLE);
                Activitymspinner2.setVisibility(View.VISIBLE);

                TheListOfServices.setVisibility(View.VISIBLE);
                YourServices.setVisibility(View.VISIBLE);

                NewMondayTime.setVisibility(View.VISIBLE);
                NewTuesdayTime.setVisibility(View.VISIBLE);
                NewWednesdayTime.setVisibility(View.VISIBLE);
                NewThursdayTime.setVisibility(View.VISIBLE);
                NewFridayTime.setVisibility(View.VISIBLE);
                NewSaturdayTime.setVisibility(View.VISIBLE);
                NewSundayTime.setVisibility(View.VISIBLE);

                NstartHM1.setVisibility(View.VISIBLE);
                NstartMM1.setVisibility(View.VISIBLE);
                NendHM1.setVisibility(View.VISIBLE);
                NendMM1.setVisibility(View.VISIBLE);
                NstartHTU1.setVisibility(View.VISIBLE);
                NstartMTU1.setVisibility(View.VISIBLE);
                NendHTU1.setVisibility(View.VISIBLE);
                NendMTU1.setVisibility(View.VISIBLE);
                NstartHW1.setVisibility(View.VISIBLE);
                NstartMW1.setVisibility(View.VISIBLE);
                NendHW1.setVisibility(View.VISIBLE);
                NendMW1.setVisibility(View.VISIBLE);
                NstartHTH1.setVisibility(View.VISIBLE);
                NstartMTH1.setVisibility(View.VISIBLE);
                NendHTH1.setVisibility(View.VISIBLE);
                NendMTH1.setVisibility(View.VISIBLE);
                NstartHF1.setVisibility(View.VISIBLE);
                NstartMF1.setVisibility(View.VISIBLE);
                NendHF1.setVisibility(View.VISIBLE);
                NendMF1.setVisibility(View.VISIBLE);
                NstartHSA1.setVisibility(View.VISIBLE);
                NstartMSA1.setVisibility(View.VISIBLE);
                NendHSA1.setVisibility(View.VISIBLE);
                NendMSA1.setVisibility(View.VISIBLE);
                NstartHSU1.setVisibility(View.VISIBLE);
                NstartMSU1.setVisibility(View.VISIBLE);
                NendHSU1.setVisibility(View.VISIBLE);
                NendMSU1.setVisibility(View.VISIBLE);
                listViewServices.setVisibility(View.VISIBLE);

                address2.setVisibility(View.GONE);
                name2.setVisibility(View.GONE);
                phoneNumber2.setVisibility(View.GONE);
                insuranceType2.setVisibility(View.GONE);
                paymentMethod2.setVisibility(View.GONE);
                break;

            case R.id.save:
                navigationView2 = (NavigationView) findViewById((R.id.navigationView2));
                nav_Menu = navigationView2.getMenu();
                nav_Menu.findItem(R.id.save).setVisible(false);
                nav_Menu.findItem(R.id.edit).setVisible(true);

                address2.setVisibility(View.VISIBLE);
                name2.setVisibility(View.VISIBLE);
                phoneNumber2.setVisibility(View.VISIBLE);
                insuranceType2.setVisibility(View.VISIBLE);
                paymentMethod2.setVisibility(View.VISIBLE);

                address.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                phoneNumber.setVisibility(View.VISIBLE);
                Activitymspinner.setVisibility(View.VISIBLE);
                Activitymspinner2.setVisibility(View.VISIBLE);

                TheListOfServices.setVisibility(View.GONE);
                YourServices.setVisibility(View.GONE);

                NewMondayTime.setVisibility(View.GONE);
                NewTuesdayTime.setVisibility(View.GONE);
                NewWednesdayTime.setVisibility(View.GONE);
                NewThursdayTime.setVisibility(View.GONE);
                NewFridayTime.setVisibility(View.GONE);
                NewSaturdayTime.setVisibility(View.GONE);
                NewSundayTime.setVisibility(View.GONE);

                NstartHM1.setVisibility(View.GONE);
                NstartMM1.setVisibility(View.GONE);
                NendHM1.setVisibility(View.GONE);
                NendMM1.setVisibility(View.GONE);
                NstartHTU1.setVisibility(View.GONE);
                NstartMTU1.setVisibility(View.GONE);
                NendHTU1.setVisibility(View.GONE);
                NendMTU1.setVisibility(View.GONE);
                NstartHW1.setVisibility(View.GONE);
                NstartMW1.setVisibility(View.GONE);
                NendHW1.setVisibility(View.GONE);
                NendMW1.setVisibility(View.GONE);
                NstartHTH1.setVisibility(View.GONE);
                NstartMTH1.setVisibility(View.GONE);
                NendHTH1.setVisibility(View.GONE);
                NendMTH1.setVisibility(View.GONE);
                NstartHF1.setVisibility(View.GONE);
                NstartMF1.setVisibility(View.GONE);
                NendHF1.setVisibility(View.GONE);
                NendMF1.setVisibility(View.GONE);
                NstartHSA1.setVisibility(View.GONE);
                NstartMSA1.setVisibility(View.GONE);
                NendHSA1.setVisibility(View.GONE);
                NendMSA1.setVisibility(View.GONE);
                NstartHSU1.setVisibility(View.GONE);
                NstartMSU1.setVisibility(View.GONE);
                NendHSU1.setVisibility(View.GONE);
                NendMSU1.setVisibility(View.GONE);

                listViewServices.setVisibility(View.GONE);

                String name4 = name.getText().toString().trim();
                String address4 = address.getText().toString().trim();
                String phoneNumber4 = phoneNumber.getText().toString().trim();
                String insuranceType4 = Activitymspinner2.getSelectedItem().toString();
                String paymentMethod4 = Activitymspinner.getSelectedItem().toString();

                if (phoneNumber4.isEmpty()) {
                    phoneNumber.setError("Phone Number is required");
                    phoneNumber.requestFocus();
                    return;
                }

                if (name4.isEmpty()) {
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }

                if (phoneNumber4.length() != 10) {
                    phoneNumber.setError("Only 10 digit phone numbers are accepted");
                    phoneNumber.requestFocus();
                    return;
                }

                try {
                    int num = Integer.parseInt(phoneNumber4);
                }catch(NumberFormatException e){
                    phoneNumber.setError("please enter a valid phone number");
                    phoneNumber.requestFocus();
                }



                Clinic clinic1 = new Clinic(name4, address4, phoneNumber4, insuranceType4, paymentMethod4);
                databaseProducts2.child(mUser.getUid()).setValue(clinic1);

                String NstartHM = NstartHM1.getSelectedItem().toString();
                String NstartMM = NstartMM1.getSelectedItem().toString();
                String NendHM = NendHM1.getSelectedItem().toString();
                String NendMM = NendMM1.getSelectedItem().toString();
                String NstartHTU = NstartHTU1.getSelectedItem().toString();
                String NstartMTU = NstartMTU1.getSelectedItem().toString();
                String NendHTU = NendHTU1.getSelectedItem().toString();
                String NendMTU = NendMTU1.getSelectedItem().toString();
                String NstartHW = NstartHW1.getSelectedItem().toString();
                String NstartMW = NstartMW1.getSelectedItem().toString();
                String NendHW = NendHW1.getSelectedItem().toString();
                String NendMW = NendMW1.getSelectedItem().toString();
                String NstartHTH = NstartHTH1.getSelectedItem().toString();
                String NstartMTH = NstartMTH1.getSelectedItem().toString();
                String NendHTH = NendHTH1.getSelectedItem().toString();
                String NendMTH = NendMTH1.getSelectedItem().toString();
                String NstartHF = NstartHF1.getSelectedItem().toString();
                String NstartMF = NstartMF1.getSelectedItem().toString();
                String NendHF = NendHF1.getSelectedItem().toString();
                String NendMF = NendMF1.getSelectedItem().toString();
                String NstartHSA = NstartHSA1.getSelectedItem().toString();
                String NstartMSA = NstartMSA1.getSelectedItem().toString();
                String NendHSA = NendHSA1.getSelectedItem().toString();
                String NendMSA = NendMSA1.getSelectedItem().toString();
                String NstartHSU = NstartHSU1.getSelectedItem().toString();
                String NstartMSU = NstartMSU1.getSelectedItem().toString();
                String NendHSU = NendHSU1.getSelectedItem().toString();
                String NendMSU = NendMSU1.getSelectedItem().toString();

                final String NstartM = NstartHM + ":" + NstartMM;
                final String NendM = NendHM + ":" + NendMM;

                final String NstartTU = NstartHTU + ":" + NstartMTU;
                final String NendTU = NendHTU + ":" + NendMTU;

                final String NstartW = NstartHW + ":" + NstartMW;
                final String NendW = NendHW + ":" + NendMW;

                final String NstartTH = NstartHTH + ":" + NstartMTH;
                final String NendTH = NendHTH + ":" + NendMTH;

                final String NstartF = NstartHF + ":" + NstartMF;
                final String NendF = NendHF + ":" + NendMF;

                final String NstartSA = NstartHSA + ":" + NstartMSA;
                final String NendSA = NendHSA + ":" + NendMSA;

                final String NstartSU = NstartHSU + ":" + NstartMSU;
                final String NendSU = NendHSU + ":" + NendMSU;

                Time time = new Time(NstartM, NendM, NstartTU, NendTU, NstartW, NendW, NstartTH, NendTH, NstartF, NendF, NstartSA, NendSA, NstartSU, NendSU);
                databaseProducts4.setValue(time);

                name2.setText("Clinic name: " + name4);
                address2.setText("Clinic address: " + address4);
                phoneNumber2.setText("Clinic phone number: " + phoneNumber4);
                insuranceType2.setText("Clinic insurance type: " + insuranceType4);
                paymentMethod2.setText("Clinic payment method: " + paymentMethod4);
                Toast.makeText(getApplicationContext(), "Clinic updated", Toast.LENGTH_LONG).show();

                break;

            case R.id.logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.delete:
                databaseProducts4.removeValue();
                databaseProducts2.child(mUser.getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference("productsEmployee").child(mUser.getUid()).removeValue();
                finish();
                startActivity(new Intent(this, createClinic.class));
                break;
        }
    }

}
