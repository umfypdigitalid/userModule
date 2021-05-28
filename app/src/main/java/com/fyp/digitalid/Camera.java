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
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Camera extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    Button captureBtn;
    Button nextBtn;
    ImageView imageView;
    Uri image_uri;
    String icimage;
    byte[] bytes;

    //todo: store image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        System.out.println("Test");
        imageView = findViewById(R.id.image_view);
        captureBtn = findViewById(R.id.capture_image_btn);
        nextBtn = findViewById(R.id.buttonNext);

        //button click
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: encode image to base64 and check null
                System.out.println("bytes: "+image_uri);
                if(image_uri!=null) {
                    Intent intent = new Intent(Camera.this, Verifying.class);
                    intent.setData(image_uri);
                    //intent.putExtra("uri",image_uri);
                    System.out.println("put extra");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please snap picture of your identity card.", Toast.LENGTH_SHORT).show();
                }

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
        System.out.println("open camera: "+image_uri);
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
            //set the image captured to our ImageView

            imageView.setImageURI(image_uri);
            /*Bitmap bitmap = null;
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
            icimage = Base64.encodeToString(bytes, Base64.DEFAULT);*/
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
    }
}