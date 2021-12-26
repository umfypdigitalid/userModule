package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class GiveAccess extends BaseActivity {

    Button btnShowQr;
    CheckBox cbname,cbic,cbbirthdate,cbemail,cbaddress;
    Boolean name = Boolean.FALSE,ic = Boolean.FALSE,bd = Boolean.FALSE,email = Boolean.FALSE,add = Boolean.FALSE;
    String username;
    BufferedInputStream is;
    String line = null;
    String result = null;
    String qrdata=" ";
    Boolean flag = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_access);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
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
                System.out.println("Flag: "+flag);
                if(flag) {
                    System.out.println("if Flag: "+flag);
                    getFromDB(username);
                    Intent intent = new Intent(GiveAccess.this, ShowQr.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Username", username);
                    bundle.putString("qrdata", qrdata);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    System.out.println("else Flag: "+flag);
                    generateqr(GiveAccess.this);
                }
            }
        });

        cbname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbname.isChecked()){
                    cbname.setTextColor(getResources().getColor(R.color.colorAccent));
                    name=Boolean.TRUE;
                    flag=Boolean.TRUE;
                }else {
                    cbname.setTextColor(getResources().getColor(R.color.black));
                    name =Boolean.FALSE;
                }
            }
        });

        cbic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbic.isChecked()){
                    cbic.setTextColor(getResources().getColor(R.color.colorAccent));
                    ic=Boolean.TRUE;
                    flag=Boolean.TRUE;
                }else {
                    cbic.setTextColor(getResources().getColor(R.color.black));
                    ic=Boolean.FALSE;
                }
            }
        });

        cbbirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbbirthdate.isChecked()){
                    cbbirthdate.setTextColor(getResources().getColor(R.color.colorAccent));
                    bd=Boolean.TRUE;
                    flag=Boolean.TRUE;
                }else {
                    cbbirthdate.setTextColor(getResources().getColor(R.color.black));
                    bd=Boolean.FALSE;
                }
            }
        });

        cbemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbemail.isChecked()){
                    cbemail.setTextColor(getResources().getColor(R.color.colorAccent));
                    email = Boolean.TRUE;
                    flag=Boolean.TRUE;
                }else {
                    cbemail.setTextColor(getResources().getColor(R.color.black));
                    email=Boolean.FALSE;
                }
            }
        });

        cbaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbaddress.isChecked()){
                    cbaddress.setTextColor(getResources().getColor(R.color.colorAccent));
                    add = Boolean.TRUE;
                    flag=Boolean.TRUE;
                }else {
                    cbaddress.setTextColor(getResources().getColor(R.color.black));
                    add=Boolean.FALSE;
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

    private String getFromDB(String username){
        //username = getIntent().getStringExtra("Username");
        System.out.println("Username: "+username);
        String showURL = "http://192.168.0.118:8080/digitalid/retrieveData.php?username="+username;
        try{

            URL url = new URL(showURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        //content
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result = sb.toString();

        }catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("Result: "+result);

        String []x = result.split("!");
        System.out.println(Arrays.toString(x));
        if(name && name!=null){
            qrdata = x[0];
        }if(ic && ic!=null){
            qrdata= qrdata + "\n"+x[1];
        }if(bd && bd!=null){
            qrdata = qrdata+ "\n"+x[2];
        }if(email && email!=null){
            qrdata = qrdata+ "\n"+x[3];
        }if(add && add!=null){
            qrdata = qrdata + "\n"+x[4];
        }
        System.out.println(qrdata);
        return qrdata;

    }

    private static void generateqr(final Activity activity) {
        //initialize alert
        AlertDialog builder = new AlertDialog.Builder(activity)
                .setTitle("QR Generator")
                .setMessage("Please select at least one personal data to generate the QR code.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        builder.show();
        /*//set title
        builder.setTitle("QR Generator");
        //set message
        builder.setMessage("Please select at least one personal data to generate the qr code");
        // yes button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });*/
    }
}