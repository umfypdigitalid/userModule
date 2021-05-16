package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {
//test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        findViewById(R.id.createBtn).setOnClickListener(this);
*/
    }

    /*@Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }*/
}