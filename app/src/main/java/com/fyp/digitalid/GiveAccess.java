package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class GiveAccess extends AppCompatActivity {

    Button btnShowQr;
    CheckBox cbname,cbic,cbbirthdate,cbemail,cbaddress;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_access);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnShowQr = findViewById(R.id.btnshowqr);
        cbname = findViewById(R.id.cbfullname);
        cbic = findViewById(R.id.cbic);
        cbbirthdate= findViewById(R.id.cbbirthdate);
        cbemail = findViewById(R.id.cbemail);
        cbaddress = findViewById(R.id.cbaddress);
        username = getIntent().getStringExtra("Username");


        btnShowQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiveAccess.this, ShowQr.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

        cbname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbname.isChecked()){
                    cbname.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    cbname.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        cbic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbic.isChecked()){
                    cbic.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    cbic.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        cbbirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbbirthdate.isChecked()){
                    cbbirthdate.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    cbbirthdate.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        cbemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbemail.isChecked()){
                    cbemail.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    cbemail.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        cbaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbaddress.isChecked()){
                    cbaddress.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {
                    cbaddress.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        setResult(RESULT_OK, intent);
        finish();
    }
}