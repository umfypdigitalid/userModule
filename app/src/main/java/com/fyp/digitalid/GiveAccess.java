package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GiveAccess extends AppCompatActivity {

    Button btnScanQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_access);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnScanQr = findViewById(R.id.scanqr);

        btnScanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiveAccess.this, QrScanner.class);
                startActivity(intent);
            }
        });
    }
}