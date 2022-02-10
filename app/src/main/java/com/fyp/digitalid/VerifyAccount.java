package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerifyAccount extends BaseActivity {

    Button btnverifynow, btncancel;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);

        btnverifynow = findViewById(R.id.btnverifynow);
        btncancel = findViewById(R.id.btncancel);
        username = getIntent().getStringExtra("Username");

        btnverifynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyAccount.this, RegisterScanner.class);
                System.out.println("verify account username: "+username);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyAccount.this, HomePage.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });


    }
}
