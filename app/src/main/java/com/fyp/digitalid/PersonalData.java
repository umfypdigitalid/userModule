package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PersonalData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //todo: show personal data get from db

    }
}