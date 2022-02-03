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
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterScanner extends BaseActivity {

    CodeScanner qrscanner;
    CodeScannerView scannerView;
    TextView resultScan;
    String username,name="Test",detail,userid,ic,fullname,dob,address,gender;
    //String claimid = "0x03f37c392bcb3e9e1ca114d09acb4239a90146a76d063c3dabcd9c1f0445991c";
    String contractaddress ;
    private String URL= "http://192.168.0.198:8080/digitalid/insertQrHistory.php";
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_scanner);

        scannerView = findViewById(R.id.qrscanner);
        qrscanner = new CodeScanner(this, scannerView);
        resultScan = findViewById(R.id.scanresult);
        username = getIntent().getStringExtra("Username");
        mDatabaseHelper = new DatabaseHelper(this);

        //detected and decode
        qrscanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("qrresult: "+result.getText());
                        resultScan.setText(result.getText());
                        //getuserId();
                        updateUserStatus();
                        try {
                            JSONObject json =new JSONObject(result.toString());
                            contractaddress = json.getString("claimId_contractAddress_obj");
                            ic = json.getString("claimId_nric_obj");
                            fullname = json.getString("claimId_name_obj");
                            dob = json.getString("claimId_dob_obj");
                            gender = json.getString("claimId_gender_obj");
                            address = json.getString("claimId_address_obj");
                            System.out.println("Contract Address: "+contractaddress);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AddData(contractaddress,ic,fullname,dob,gender,address);
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

    private void updateUserStatus() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String showURL = "http://192.168.0.198:8080/digitalid/updateuserstatus.php?username="+username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, showURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Result: "+response);
                if (response.equals("Success")) {
                    Toast.makeText(RegisterScanner.this, "User Status Updated", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(RegisterScanner.this, "User Status Updated Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterScanner.this, error.getMessage().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        intent.putExtra("Username", username);
        startActivity(intent);
        finish();
    }

    private void verified(final Activity activity) {
        //initialize alert
        AlertDialog builder = new AlertDialog.Builder(activity)
                .setTitle("Verified")
                .setMessage("Your account have been verified")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(activity, HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Username", username);
                        activity.startActivity(intent);
                    }
                }).create();
        builder.show();
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
                Toast.makeText(RegisterScanner.this, "Camera Permission is Required.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    protected void putData(String detail, String userid){
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
                putData(detail,userid);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void AddData(String contractaddress, String ic, String fullname, String dob, String gender, String address) {
        boolean insertData = mDatabaseHelper.addData(contractaddress,ic,fullname,dob,gender,address);

        if(insertData){
            Toast.makeText(getApplicationContext(),"Data Successfully Inserted!", Toast.LENGTH_SHORT).show();
            verified(RegisterScanner.this);
        } else {
            Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


}