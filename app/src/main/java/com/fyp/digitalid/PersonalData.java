package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class PersonalData extends BaseActivity {

    TextView textView1, textView2, textView3, textView4, textView5, textViewUserStatus;
    //private List<UserData> userDataList;
    String username ;
    BufferedInputStream is;
    String line = null;
    String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        //getSupportActionBar().setTitle("Personal Data");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        textView1 = findViewById(R.id.textViewFullName);
        textView2 = findViewById(R.id.textViewIC);
        textView3 = findViewById(R.id.textViewBd);
        textView4 = findViewById(R.id.textViewEmail);
        textView5 = findViewById(R.id.textViewAddress);
        textViewUserStatus = findViewById(R.id.tvuserstatus);

        getData();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        setResult(RESULT_OK, intent);
        finish();
    }


    private void getData(){

        //connection
        username = getIntent().getStringExtra("Username");
        System.out.println("Username: "+username);
        String showURL = "http://192.168.0.198:8080/digitalid/retrieveData.php?username="+username;
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

        String fullName = x[0];
        String ic =x[1];
        String bd = x[2];
        String email =x[3];
        String address =x[4];
        String userstatus =x[5];
        textView1.setText(fullName);
        textView2.setText(ic);
        textView3.setText(bd);
        textView4.setText(email);
        textView5.setText(address);
        textViewUserStatus.setText(userstatus);

    }
}