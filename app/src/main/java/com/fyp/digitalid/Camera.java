package com.fyp.digitalid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Camera extends BaseActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    String userstatus = "VERIFYING";
    Button captureBtn;
    Button nextBtn;
    ImageView imageView;
    Uri image_uri;
    String icimage,username;
    byte[] bytes;

    private String URL= "http://192.168.0.198:8080/digitalid/updateIcImage.php";

    //todo: store image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        System.out.println("Test");
        imageView = findViewById(R.id.image_view);
        captureBtn = findViewById(R.id.capture_image_btn);
        nextBtn = findViewById(R.id.buttonNext);
        username = getIntent().getStringExtra("Username");
        System.out.println("camera username: "+username);



        //button click
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtn.setClickable(false);
                putdata();
                /*if(!icimage.equals("")&&!username.equals("") ) {
                    //progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //Thread thread = new Thread() {
                                //public void run() {
                                    //Creating array for parameters
                                    String[] field = new String[3];
                                    field[0] = "username";
                                    field[1] = "icimage";
                                    field[2] = "userstatus";
                                    //Creating array for data
                                    String[] data = new String[3];
                                    data[0] = username;
                                    data[1] = icimage;
                                    data[2] = userstatus;
                                    System.out.println("Username: "+username+"\nIcimage: "+icimage);
                                    //192.168.0.198
                                    PutData putData = new PutData(URL, "POST", field, data);
                                    //PutData putData = new PutData("https://digitalidum.000webhostapp.com/lq/signup.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            //progressBar.setVisibility(View.GONE);
                                            String result = putData.getResult();
                                            if (result.equals("Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), Verifying.class);
                                                intent.putExtra("Username", username);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                            Log.i("PutData", result);
                                        }
                                    }
                                }

                            //};
                       //     thread.start();
                       // }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please snap picture of your identity card", Toast.LENGTH_SHORT).show();
                }*/
            }
        });



        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if system os is >= marshmallow, request runtime permission
                //todo: choose from gallery
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        //permission not enabled, request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permissions
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        openCamera();
                    }
                }
                else {
                    //system os < marshmallow
                    openCamera();
                }
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    //handling permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera();
                }
                else {
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when image was captured from camera
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("on activity result");
        System.out.println("Result code: " + resultCode);
        if (resultCode == RESULT_OK) {
            imageView.setImageURI(image_uri);
            Thread thread = new Thread() {
                public void run() {
                    System.out.println("Thread Running");
                    //set the image captured to our ImageView
                    nextBtn.setClickable(false);
                    convertbase64();
                }
            };
            thread.start();
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    //set the image captured to our ImageView
                    imageView.setImageURI(image_uri);
                    convertbase64();
                }
            }).start();*/

        }
    }
    protected void convertbase64(){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image_uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //compress bitmap
        System.out.println("before compress");
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        System.out.println("After compress");
        bytes = stream.toByteArray();
        // Get base64 encoded string
        icimage = Base64.encodeToString(bytes, Base64.DEFAULT);
        System.out.println("Done convert to base64");
        nextBtn.setClickable(true);
    }

    protected void putdata(){
        if(icimage!=null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Success")) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Verifying.class);
                        intent.putExtra("Username",username);
                        startActivity(intent);
                        finish();
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
                    data.put("username",username);
                    data.put("icimage",icimage);
                    data.put("userstatus", userstatus);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(), "Please snap picture of your identity card", Toast.LENGTH_SHORT).show();
        }
    }
}