package com.fyp.digitalid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.TextureView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword,
             textInputEditTextConfirmPassword, textInputEditTextEmail, textInputEditTextIc, textInputEditTextBirthdate, textInputEditTextAddress;
    TextInputLayout textInputLayoutGender;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    DatePickerDialog picker;
    //String icimage;
    String fullname, username, password, email, ic, dob, address,gender;
    TextView emailerror,passwordError,confirmPassError;
    private String URL = "http://192.168.0.198:8080/digitalid/signup3.php";
   /* Uri image_uri ;
    byte[] bytes;*/
    AwesomeValidation awesomeValidation;
    FirebaseAuth fAuth;
    AutoCompleteTextView dropdowntext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextConfirmPassword = findViewById(R.id.confirmpassword);
        textInputEditTextIc = findViewById(R.id.ic);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextBirthdate = findViewById(R.id.birthdate);
        textInputEditTextAddress = findViewById(R.id.address);
        //textInputLayoutGender = findViewById(R.id.textInputLayoutGender);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();
        dropdowntext = findViewById(R.id.dropdowntext);


        //image_uri=getIntent().getData();
        //convertbase64();
        //icimage = Base64.encodeToString(bytes, Base64.DEFAULT);
        //icimage = getIntent().getStringExtra("icimage");
        fullname = username = password = email = ic = gender = dob = address ="";

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //awesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.fullname, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.ic, RegexTemplate.NOT_EMPTY,R.string.invalid_ic);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY,R.string.invalid_username);
        awesomeValidation.addValidation(this, R.id.password,".{6,}",R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.confirmpassword,R.id.password, R.string.invalid_confirm_password);
        awesomeValidation.addValidation(this, R.id.email, RegexTemplate.NOT_EMPTY,R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.dropdowntext, RegexTemplate.NOT_EMPTY,R.string.invalid_gender);
        awesomeValidation.addValidation(this, R.id.birthdate, RegexTemplate.NOT_EMPTY,R.string.invalid_dob);
        awesomeValidation.addValidation(this, R.id.address, RegexTemplate.NOT_EMPTY,R.string.invalid_address);

        dropdowntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] gender = new String[]{
                        "M",
                        "F"
                };

                ArrayAdapter<String> adapter =  new ArrayAdapter<>(
                        SignUp.this,R.
                        layout.dropdown_menu,
                        gender
                );

                dropdowntext.setAdapter(adapter);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    //Toast.makeText(getApplicationContext(),"Validation Success", Toast.LENGTH_SHORT).show();

                    fullname = String.valueOf(textInputEditTextFullname.getText());
                    username = String.valueOf(textInputEditTextUsername.getText());
                    password = String.valueOf(textInputEditTextPassword.getText());
                    ic = String.valueOf(textInputEditTextIc.getText());
                    email = String.valueOf(textInputEditTextEmail.getText());
                    dob = String.valueOf(textInputEditTextBirthdate.getText());
                    address = String.valueOf(textInputEditTextAddress.getText());
                    gender = String.valueOf(dropdowntext.getText());
                /*validateEmail();
                validatePassword();*/
                /* if(!password.equals(reenterPassword)){
                    //toast
                }*/

                /*email = String.valueOf(textInputEditTextEmail.getText());
                birthdate = String.valueOf(textInputEditTextBirthdate.getText());
                address = String.valueOf(textInputEditTextAddress.getText());*/

                    //if (!fullname.equals("") && !username.equals("") && !dob.equals("") && !address.equals("") && !gender.equals("") && !password.equals("") && !ic.equals("") && !email.equals("")) {
                        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "User Created.", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(getApplicationContext(), "Error Creating User! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    prompterror(SignUp.this,task.getException().getMessage());
                                }
                            }
                        });
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
                                    prompterror(SignUp.this,response);
                                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> data = new HashMap<>();
                                data.put("fullname", fullname);
                                data.put("username", username);
                                data.put("password", password);
                                data.put("ic", ic);
                                data.put("email",email);
                                data.put("dob",dob);
                                data.put("address",address);
                                data.put("gender",gender);
                                return data;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_SHORT).show();
                }
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
                                textInputEditTextBirthdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
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

    private void prompterror(final Activity activity, String error){
        AlertDialog builder = new AlertDialog.Builder(activity)
                .setTitle("Failed to Sign Up")
                .setMessage(error)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(activity, SignUp.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }
                }).create();
        builder.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUp.this,Login.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void validateEmail() {
        String emailInput = textInputEditTextEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            emailerror.setText("Field can't be empty");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailerror.setText("Please enter a valid email address");
        } else {
            emailerror.setError(null);
        }
    }
    private void validatePassword() {
        String passwordInput = textInputEditTextPassword.getText().toString().trim();
        String ConfitmpasswordInput = textInputEditTextConfirmPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            passwordError.setText("Field can't be empty");
        }  if (passwordInput.length()<5) {
            passwordError.setText("Password must be at least 5 characters");
        }
        if (!passwordInput.equals(ConfitmpasswordInput)) {
            confirmPassError.setText("Password Would Not be matched");
        }else {
            confirmPassError.setText("Password Matched");
        }
    }

}