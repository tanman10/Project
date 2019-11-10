package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PersonList extends ArrayAdapter<Person> {

    private Activity context;
    List<Person> persons;

    public PersonList(Activity context, List<Person> persons) {
        super(context, R.layout.activity_person_list, persons);
        this.context = context;
        this.persons = persons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_person_list, null, true);

        TextView textViewFirstName = (TextView) listViewItem.findViewById(R.id.textViewNameFirst);
        TextView textViewLastName = (TextView) listViewItem.findViewById(R.id.textViewNameLast);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textViewEmail2);
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.textViewRole2);
        
        Person person = persons.get(position);
        textViewFirstName.setText(person.getFirstName());
        textViewLastName.setText(person.getLastName());
        textViewEmail.setText(person.getEmail());
        textViewRole.setText(person.getRole());
        return listViewItem;
    }
}
