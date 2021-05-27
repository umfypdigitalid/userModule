package com.fyp.digitalid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    TextView textViewLogOut, textViewUsername;
    Button btnPersonalData, btnGenerateQR, btnScanQR;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        textViewLogOut = findViewById(R.id.textViewLogout);
        btnPersonalData = findViewById(R.id.btnpersonaldata);
        btnGenerateQR = findViewById(R.id.btngiveaccess);
        btnScanQR = findViewById(R.id.btnscanqr);
        textViewUsername = findViewById(R.id.textViewUsername);
        username = getIntent().getStringExtra("Username");
        textViewUsername.setText(username);

        //todo : username get from db
        textViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() ,LogOut.class);
                startActivity(intent);
                finish();
            }
        });

        btnPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, PersonalData.class);
                intent.putExtra("Username", username);
                startActivity(intent);

            }
        });

        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GiveAccess.class);
                startActivityForResult(intent,999);
                intent.putExtra("Username", username);
                startActivity(intent);
/*
                finish();
*/
            }
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), QrScanner.class),999);
                /*Intent intent = new Intent(getApplicationContext(), QrScanner.class);
                intent.putExtra("Username", username);
                startActivity(intent);*/
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            username = data.getStringExtra("Username");
        }
    }
}