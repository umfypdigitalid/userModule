package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalData extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4;
    private List<UserData> userDataList;
    String username ;
    private static final String showURL = "http://192.168.0.198:8080/digitalid/retrieveData.php";
    String name;
    //ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //listView=(ListView)findViewById(R.id.lview) ;

        //todo: show personal data get from db
        username = getIntent().getStringExtra("Username");
        /*userDataList = new ArrayList<>();*/
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        textView1 = findViewById(R.id.textViewFullName);
        textView2 = findViewById(R.id.textViewIC);
        textView3 = findViewById(R.id.textViewEmail);
        textView4 = findViewById(R.id.textViewAddress);

        getData();

    }

    private void getData(){

        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, showURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PersonalData.this,response.trim(),Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PersonalData.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PersonalData.this);
        requestQueue.add(stringRequest);*/

        //connection
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
        //name=result;
        //textView1.setText(result);
        System.out.println("Result: "+result);

        String []x = result.split("!");
        System.out.println(Arrays.toString(x));

        String fullName = x[0];
        String ic =x[1];
        String email =x[2];
        String address =x[3];
        textView1.setText(fullName);
        textView2.setText(ic);
        textView3.setText(email);
        textView4.setText(address);



        //JSON
        //try {





    }
}