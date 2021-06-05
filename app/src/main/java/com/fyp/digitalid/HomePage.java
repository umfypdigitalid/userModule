package com.fyp.digitalid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends BaseActivity {

    TextView textViewLogOut, textViewUsername, textViewContactUs;
    Button btnPersonalData, btnGenerateQR, btnScanQR;
    public static String username;
    String userstatus;
    String verified = "VERIFIED";
    String unverified = "UNVERIFIED";
    String verifying = "VERIFYING";
    BufferedInputStream is;
    String line = null;
    String result = null;
    DrawerLayout drawerLayout;

    //private Timer timer;
    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        //textViewLogOut = findViewById(R.id.textViewLogout);
        btnPersonalData = findViewById(R.id.btnpersonaldata);
        btnGenerateQR = findViewById(R.id.btngiveaccess);
        btnScanQR = findViewById(R.id.btnscanqr);
        textViewUsername = findViewById(R.id.textViewUsername);
        username = getIntent().getStringExtra("Username");
        textViewUsername.setText(username);
        textViewContactUs = findViewById(R.id.tvphone);

        //userstatus=verified;
        //getUserStatus();
        getStatus();

       /* textViewContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactUs.class);
                intent.putExtra("Username", username);
                startActivityForResult(intent,999);
            }
        });*/

        /*textViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() ,LogOut.class);
                startActivity(intent);
                finish();
            }
        });*/

        btnPersonalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(HomePage.this, PersonalData.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                    //finish();
            }
        });


        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userstatus.equalsIgnoreCase(verified)) {
                    Intent intent = new Intent(getApplicationContext(), GiveAccess.class);
                    intent.putExtra("Username", username);
                    startActivityForResult(intent,999);
                    //startActivity(intent);
                } if(userstatus.equalsIgnoreCase(unverified)){
                    unverified();
                }if(userstatus.equalsIgnoreCase(verifying)){
                    verifying();
                }

            }
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("userstatus scanqr: "+userstatus);
                if(userstatus.equalsIgnoreCase(verified)) {
                    Intent intent = new Intent(getApplicationContext(), QrScanner.class);
                    intent.putExtra("Username", username);
                    startActivityForResult(intent,999);
                    /*Intent intent = new Intent(getApplicationContext(), QrScanner.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);*/
                } if(userstatus.equalsIgnoreCase(unverified)){
                    unverified();
                } if(userstatus.equalsIgnoreCase(verifying)){
                    verifying();
                }
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

    protected void unverified(){
        Intent intent = new Intent(HomePage.this, VerifyAccount.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    protected void verifying(){
        Intent intent = new Intent(HomePage.this, Verifying.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }


   /* protected void getUserStatus(){
        String showURL = "http://192.168.0.198:8080/digitalid/homepage.php?username="+username;
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
        userstatus =result;
        //userstatus = verified;
    }*/

    protected void getStatus(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String showURL = "http://192.168.0.198:8080/digitalid/homepage.php?username="+username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, showURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Result: "+response);
                userstatus=response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //if drawer is open
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        //recreate activity
        recreate();
    }

    public void ClickDashboard(View view){
        //redirect activity to dashboard
        redirectActivity(this,PersonalData.class);
    }

    public void ClickAboutUs(View view){
        redirectActivity(this,ContactUs.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(final Activity activity){
        //initialize alert
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        //set message
        builder.setMessage("Are you sure you want to logout ?");
        // yes button
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                redirectActivity(activity, LogOut.class);
                /*//Finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);*/
            }
        });
        //Negative no buton
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        //Show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Username", username);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }
}