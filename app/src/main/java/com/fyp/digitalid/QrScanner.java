package com.fyp.digitalid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QrScanner extends BaseActivity {

    CodeScanner qrscanner;
    CodeScannerView scannerView;
    TextView resultScan;
    //Button btnShowQr;
    StringBuffer field = new StringBuffer();
    String username,name="Test",detail,userid,uid,contractAddress,type,selection,
            ic,fullname,dob,address,gender,reply,retry;
    private String URL= "http://192.168.0.198:8080/digitalid/insertQrHistory1.php";
    private String posturl="http://192.168.0.198:8080/digitalid/insertJson.php";
    DatabaseHelper mDatabaseHelper;
    BufferedInputStream is;
    String line = null;
    String result = null;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        scannerView = findViewById(R.id.qrscanner);
        qrscanner = new CodeScanner(this, scannerView);
        resultScan = findViewById(R.id.scanresult);
        username = getIntent().getStringExtra("Username");
        mDatabaseHelper = new DatabaseHelper(this);
        progressBar = findViewById(R.id.progress);


        //detected and decode
        qrscanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        System.out.println("qrresult: "+result.getText());
                        resultScan.setText(result.getText());
                        try {
                            JSONObject json =new JSONObject(result.toString());
                            retry = json.getString("retry");
                            uid = json.getString("uid");
                            type = json.getString("type");
                            if(type.equals("1")){
                                selection=json.getString("selection");
                                System.out.println("Selection: "+selection);
                            }
                            name = json.getString("name");
                            reply = json.getString("reply");

                            System.out.println("UID: "+ uid +"\n Type: "+type);
                            try {
                                promptPermission(QrScanner.this);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrscanner.startPreview();
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

    @Override
    public void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                qrscanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(QrScanner.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    protected void putData(String detail, String userid, String username){
        System.out.println("putdata userid: "+userid);
        if(detail!=null&&name!=null&&userid!=null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    if (response.equals("Success")) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        /*Intent intent = new Intent(getApplicationContext(), Verifying.class);
                        intent.putExtra("Username",username);
                        startActivity(intent);
                        finish();*/
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("userid",userid);
                    data.put("name",name);
                    data.put("detail", detail);
                    data.put("username",username);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(), "Please scan the QR code again", Toast.LENGTH_SHORT).show();
        }
    }
    protected void getuserId(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String showURL = "http://192.168.0.198:8080/digitalid/userid.php?username="+username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, showURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    userid = response;
                    System.out.println("userid from response: "+userid);
                    detail= String.valueOf(resultScan.getText());

                putData(detail,userid,username);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    protected void httpPostJson (String selection, String url) throws JSONException {
        System.out.println("post selection: "+selection);
        getContractAddress();
        getData();
        JSONObject json = new JSONObject();
        JSONObject data = new JSONObject();
        json.put("retry", retry);
        json.put("uid", uid);
        json.put("type", type);
        json.put("contractAddress",contractAddress);

        if(selection.charAt(0)=='1') {
            System.out.println("IC in json: "+ic);
            data.put("IC", ic);
        }
        if(selection.charAt(1)=='1') {
            data.put("Name", fullname);
        }
        if(selection.charAt(2)=='1') {
            data.put("DOB", dob);
        }
        if(selection.charAt(3)=='1') {
            data.put("Gender", gender);
        }
        if(selection.charAt(4)=='1') {
            data.put("Address", address);
        }
        json.put("data", data);
        System.out.println("JSON request: "+json);

        if(json!=null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    if (response.equals("Success")) {
                        getuserId();
                        Intent intent = new Intent(QrScanner.this, HomePage.class);
                        intent.putExtra("Username", username);
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);
                        //putData(detail,userid,username);

                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("json", String.valueOf(json));
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(), "Post Request is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void getContractAddress() {
        Cursor res = mDatabaseHelper.getData();
        if(res.getCount()==0){
            Toast.makeText(getApplicationContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID: " + res.getString(0)+"\n");
            buffer.append("Contract Address: " + res.getString(1)+"\n");
            contractAddress=res.getString(1);
        }
        System.out.println(buffer.toString());
    }

    protected void promptPermission(Activity activity) throws JSONException {
        //check accept or reject
        //initialize alert
        if (selection.charAt(0) == '1') {
            field.append("- NRIC" + "\n");
        }
        if (selection.charAt(1) == '1') {
            field.append("- Fullname" + "\n");
        }
        if (selection.charAt(2) == '1') {
            field.append("- Birthdate" + "\n");
        }
        if (selection.charAt(3) == '1') {
            field.append("- Gender" + "\n");
        }
        if (selection.charAt(4) == '1') {
            field.append("- Home Address" + "\n");
        }

        if (type.equals("1")) {
            progressBar.setVisibility(View.GONE);
            AlertDialog builder = new AlertDialog.Builder(activity)
                    .setTitle("Access Permission")
                    .setMessage("This e-Service would like to request for the information from your profile:" + field.toString())
                    .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Third Party Website Log In Failed.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                progressBar.setVisibility(View.VISIBLE);
                                httpPostJson(selection, posturl);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).create();
            builder.show();
        }
        if (type.equals("2")){
            progressBar.setVisibility(View.GONE);
            AlertDialog builder = new AlertDialog.Builder(activity)
                    .setTitle("Access Permission")
                    .setMessage("This e-Service would like to verify your identity.")
                    .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Identity Verification Failed.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                progressBar.setVisibility(View.VISIBLE);
                                httpPostJson(selection, posturl);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).create();
            builder.show();
        }
    }

    protected void getData(){
        username = getIntent().getStringExtra("Username");
        System.out.println("Username: "+username);
        String showURL = "http://192.168.0.198:8080/digitalid/retriveDataScanner.php?username="+username;
        try{
            java.net.URL url = new URL(showURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine())!=null){
                sb.append(line);
            }
            is.close();
            result = sb.toString();

        }catch (Exception ex){
            ex.printStackTrace();
        }

        System.out.println("Result: "+result);

        String []x = result.split(" !");
        System.out.println(Arrays.toString(x));

        fullname = x[0];
        ic =x[1];
        gender = x[2];
        dob = x[3];
        address =x[4];
    }
}