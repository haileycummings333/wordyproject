package com.example.wordy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.FirebaseDatabase;

public class Words extends AppCompatActivity{
    FirebaseDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }



}
