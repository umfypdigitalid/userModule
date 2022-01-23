package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactUs extends BaseActivity {

    String username;
    DrawerLayout drawerLayout;
    TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        username = getIntent().getStringExtra("Username");
        drawerLayout = findViewById(R.id.drawer_layout);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewUsername.setText(username);

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

    public void ClickHistoryQR(View view){ HomePage.redirectActivity(this,QrHistory.class); }

    public void ClickAboutUs(View view){
        recreate();
    }

    public void ClickLogout(View view){
        HomePage.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HomePage.closeDrawer(drawerLayout);
    }
}