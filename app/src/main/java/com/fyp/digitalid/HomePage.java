package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    TextView textViewLogOut, textViewUsername;
    Button btnPersonalData, btnGiveAccess;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        textViewLogOut = findViewById(R.id.textViewLogout);
        btnPersonalData = findViewById(R.id.btnpersonaldata);
        btnGiveAccess = findViewById(R.id.btngiveaccess);
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
/*
                finish();
*/
            }
        });

        btnGiveAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GiveAccess.class);
                startActivity(intent);
/*
                finish();
*/
            }
        });


    }
}