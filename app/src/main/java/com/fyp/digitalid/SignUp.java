package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail, textInputEditTextIc, textInputEditTextBirthdate, textInputEditTextAddress;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    DatePickerDialog picker;
    String icimage;
    String fullname, username, password, email, ic, birthdate, address;
    private String URL = "http://192.168.0.198:8080/digitalid/signup2.php";
   /* Uri image_uri ;
    byte[] bytes;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextIc = findViewById(R.id.ic);
        textInputEditTextBirthdate = findViewById(R.id.birthdate);
        textInputEditTextAddress = findViewById(R.id.address);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);
        //image_uri=getIntent().getData();
        //convertbase64();
        //icimage = Base64.encodeToString(bytes, Base64.DEFAULT);
        //icimage = getIntent().getStringExtra("icimage");
        fullname = username = password = email = ic = birthdate = address ="";
        System.out.println("Signup activity: "+icimage);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = String.valueOf(textInputEditTextFullname.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
               /* if(!password.equals(reenterPassword)){
                    //toast
                }*/
                email = String.valueOf(textInputEditTextEmail.getText());
                ic = String.valueOf(textInputEditTextIc.getText());
                birthdate = String.valueOf(textInputEditTextBirthdate.getText());
                address = String.valueOf(textInputEditTextAddress.getText());

                if(!fullname.equals("")&&!username.equals("") &&!password.equals("")&&!email.equals("")&&!ic.equals("")&&!birthdate.equals("")&&!address.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Sign Up Success")) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                buttonSignUp.setClickable(false);
                                Intent intent = new Intent(getApplicationContext(), Login.class);
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
                            data.put("fullname",fullname);
                            data.put("username",username);
                            data.put("password",password);
                            data.put("ic",ic);
                            data.put("birthdate",birthdate);
                            data.put("email",email);
                            data.put("address",address);
                            //data.put("icimage",icimage);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textInputEditTextBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textInputEditTextBirthdate.getWindowToken(), 0);
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(SignUp.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textInputEditTextBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


       /* buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, username, password, email, ic, birthdate, address;
                fullname = String.valueOf(textInputEditTextFullname.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                ic = String.valueOf(textInputEditTextIc.getText());
                birthdate = String.valueOf(textInputEditTextBirthdate.getText());
                address = String.valueOf(textInputEditTextAddress.getText());

                if(!fullname.equals("")&&!username.equals("") &&!password.equals("")&&!email.equals("")&&!ic.equals("")&&!birthdate.equals("")&&!address.equals("")&&!icimage.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Creating array for parameters
                            String[] field = new String[8];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "ic";
                            field[4] = "birthdate";
                            field[5] = "email";
                            field[6] = "address";
                            field[7] = "icimage";
                            //Creating array for data
                            String[] data = new String[8];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = ic;
                            data[4] = birthdate;
                            data[5] = email;
                            data[6] = address;
                            data[7] = icimage;
                            //192.168.0.198
                            PutData putData = new PutData("http://192.168.0.198:8080/digitalid/signup.php", "POST", field, data);
                            //PutData putData = new PutData("https://digitalidum.000webhostapp.com/lq/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Verifying.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    Log.i("PutData", result);
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });*/


    }

}