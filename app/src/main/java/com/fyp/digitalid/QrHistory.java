package com.fyp.digitalid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QrHistory extends BaseActivity {

    String username;
    DrawerLayout drawerLayout;
    ListView listView;
    LinearLayout linearLayout;
    TextView textViewUsername;
    private List<History> historys;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_history);
        //mToolbar = findViewById(R.id.dashboard_toolbar);
        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        username = getIntent().getStringExtra("Username");
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.historys_recyclerView);
        manager = new GridLayoutManager(QrHistory.this, 2);
        recyclerView.setLayoutManager(manager);
        historys = new ArrayList<>();
        textViewUsername = findViewById(R.id.textViewUsername);
        username = getIntent().getStringExtra("Username");
        textViewUsername.setText(username);

        getHistory();

        //linearLayout = findViewById(R.id.spectrum);
        //listView = findViewById(R.id.historylist);

        /*linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), leqing.class);
                intent.putExtra("Username",username);
                startActivity(intent);
                finish();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void ClickMenu(View view){
        HomePage.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        HomePage.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        HomePage.redirectActivity(this,HomePage.class);
    }

    public void ClickDashboard(View view){
        HomePage.redirectActivity(this,PersonalData.class);
    }

    public void ClickHistoryQR(View view){
        recreate();
    }

    public void ClickAboutUs(View view){ HomePage.redirectActivity(this,ContactUs.class); }

    public void ClickLogout(View view){
        HomePage.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer(drawerLayout);
    }

    private void getHistory(){
        progressBar.setVisibility(View.VISIBLE);
        String URL = "http://192.168.0.118:8080/digitalid/retrievehistory2.php?username="+username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for(int i = 0; i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                String name = object.getString("name");
                                String scannedon = object.get("scannedon").toString();
                                String fullname = object.getString("fullname");
                                String ic = object.getString("ic");
                                History history = new History(name, scannedon, fullname, ic);
                                historys.add(history);
                                System.out.println("Add into histroy list");
                            }
                        } catch ( Exception e) {
                            Toast.makeText(QrHistory.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                        mAdapter = new RecyclerAdapter(QrHistory.this, historys);
                        recyclerView.setAdapter(mAdapter);
                        System.out.println("set adapter");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QrHistory.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(QrHistory.this).add(stringRequest);
        progressBar.setVisibility(View.INVISIBLE);

    }
}