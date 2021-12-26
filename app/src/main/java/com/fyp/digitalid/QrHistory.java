package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class QrHistory extends BaseActivity {

    String username;
    DrawerLayout drawerLayout;
    ListView listView;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_history);
        username = getIntent().getStringExtra("Username");
        drawerLayout = findViewById(R.id.drawer_layout);
        linearLayout = findViewById(R.id.spectrum);
        //listView = findViewById(R.id.historylist);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), leqing.class);
                intent.putExtra("Username",username);
                startActivity(intent);
                finish();
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
}