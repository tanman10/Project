package com.example.walkinclinic;

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

public class Time {

    private String Mtime;
    private String Mtime2;
    private String TUtime;
    private String TUtime2;
    private String Wtime;
    private String Wtime2;
    private String THtime;
    private String THtime2;
    private String Ftime;
    private String Ftime2;
    private String SAtime;
    private String SAtime2;
    private String SUtime;
    private String SUtime2;

    public Time(){

    }

    public  Time(String Mtime, String Mtime2, String TUtime, String TUtime2, String Wtime, String Wtime2, String THtime, String THtime2, String Ftime, String Ftime2, String SAtime, String SAtime2, String SUtime, String SUtime2){
        this.Mtime = Mtime;
        this.Mtime2 = Mtime2;
        this.TUtime = TUtime;
        this.TUtime2 = TUtime2;
        this.Wtime = Wtime;
        this.Wtime2 = Wtime2;
        this.THtime = THtime;
        this.THtime2 = THtime2;
        this.Ftime = Ftime;
        this.Ftime2 = Ftime2;
        this.SAtime = SAtime;
        this.SAtime2 = SAtime2;
        this.SUtime = SUtime;
        this.SUtime2 = SUtime2;
    }

    public String getMtime() {
        return Mtime;
    }

    public void setMtime(String mtime) {
        Mtime = mtime;
    }

    public String getMtime2() {
        return Mtime2;
    }

    public void setMtime2(String mtime2) {
        Mtime2 = mtime2;
    }

    public String getTUtime() {
        return TUtime;
    }

    public void setTUtime(String TUtime) {
        this.TUtime = TUtime;
    }

    public String getTUtime2() {
        return TUtime2;
    }

    public void setTUtime2(String TUtime2) {
        this.TUtime2 = TUtime2;
    }

    public String getWtime() {
        return Wtime;
    }

    public void setWtime(String wtime) {
        Wtime = wtime;
    }

    public String getWtime2() {
        return Wtime2;
    }

    public void setWtime2(String wtime2) {
        Wtime2 = wtime2;
    }

    public String getTHtime() {
        return THtime;
    }

    public void setTHtime(String THtime) {
        this.THtime = THtime;
    }

    public String getTHtime2() {
        return THtime2;
    }

    public void setTHtime2(String THtime2) {
        this.THtime2 = THtime2;
    }

    public String getFtime() {
        return Ftime;
    }

    public void setFtime(String ftime) {
        Ftime = ftime;
    }

    public String getFtime2() {
        return Ftime2;
    }

    public void setFtime2(String ftime2) {
        Ftime2 = ftime2;
    }

    public String getSAtime() {
        return SAtime;
    }

    public void setSAtime(String SAtime) {
        this.SAtime = SAtime;
    }

    public String getSAtime2() {
        return SAtime2;
    }

    public void setSAtime2(String SAtime2) {
        this.SAtime2 = SAtime2;
    }

    public String getSUtime() {
        return SUtime;
    }

    public void setSUtime(String SUtime) {
        this.SUtime = SUtime;
    }

    public String getSUtime2() {
        return SUtime2;
    }

    public void setSUtime2(String SUtime2) {
        this.SUtime2 = SUtime2;
    }
}
