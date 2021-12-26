package com.fyp.digitalid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {

    TextInputEditText  textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonLogin;
    TextView textViewSignUp, textViewForgotPassword;
    ProgressBar progressBar;
    private String username, password, email;
    private String URL = "http://192.168.0.118:8080/digitalid/login.php";
    private Timer timer;
    FirebaseAuth fAuth;


    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = password = "";

        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);

        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        textViewForgotPassword = findViewById(R.id.forgetPassword);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
/*
                finish();
*/
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Reset password");
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! Rest Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the Dialog
                    }
                });passwordResetDialog.create().show();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                if (!email.equals("") && !password.equals("")) {
                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this,"Logged in Successfully.", Toast.LENGTH_SHORT).show();
                                getusername();

                            } else {
                                Toast.makeText(Login.this,"Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if (response.equals("Login Success")) {
//                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
//                                String username = textInputEditTextUsername.getText().toString();
//                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
//                                intent.putExtra("Username", username);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> data = new HashMap<>();
//                            data.put("username", username);
//                            data.put("password", password);
//                            return data;
//                        }
//                    };
//                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                    requestQueue.add(stringRequest);

            } else {
                Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();

            }
                progressBar.setVisibility(View.GONE);
        }
        });

    }

    private void getusername(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String showURL = "http://192.168.0.118:8080/digitalid/username.php?email="+email;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, showURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Result: "+response);
                username=response;
                System.out.println("username: "+username);
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                intent.putExtra("Username", username);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

    }


        /*buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if(!username.equals("") &&!password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            *//*String username = textInputEditTextUsername.getText().toString();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            intent.putExtra("Username", username);
                            startActivity(intent);*//*
                            //PutData putData = new PutData("https://digitalidum.000webhostapp.com/lq/login.php", "POST", field, data);
                            PutData putData = new PutData("http://192.168.0.198:8080/digitalid/login.php", "POST", field, data);
                            //192.168.0.198
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        String username = textInputEditTextUsername.getText().toString();
                                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                        intent.putExtra("Username", username);
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
   /* @Override
    protected void onPause() {
        super.onPause();

        timer = new Timer();
        Log.i("HomePage", "Invoking logout timer");
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.schedule(logoutTimeTask, 5000); //(300000) auto logout in 5 minutes
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            Log.i("HomePage", "cancel timer");
            timer = null;
        }
    }

    private class LogOutTimerTask extends TimerTask {

        @Override
        public void run() {

            //redirect user to login screen
            Intent i = new Intent(Login.this, LogOut.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }*/

