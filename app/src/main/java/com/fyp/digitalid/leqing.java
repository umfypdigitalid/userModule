package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class leqing extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leqing);
        username = getIntent().getStringExtra("Username");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),QrHistory.class);
        intent.putExtra("Username", username);
        startActivity(intent);
        finish();
    }
}