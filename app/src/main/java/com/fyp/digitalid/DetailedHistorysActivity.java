package com.fyp.digitalid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailedHistorysActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    String username;
    //private ImageView mImage;
    private TextView mName, mScannedon, mFullname, mIc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_historys);
        System.out.println("Activity detailed historys");

        //mToolbar = findViewById(R.id.toolbar);
        //mImage = findViewById(R.id.image_view);
        mName = findViewById(R.id.name);
        mScannedon = findViewById(R.id.scannedon);
        mFullname = findViewById(R.id.fullname);
        mIc = findViewById(R.id.ic);

        // Setting up action bar
        //setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        //mActionBar.setDisplayHomeAsUpEnabled(true);
        //mActionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));

        // Catching incoming intent
        Intent intent = getIntent();
        /*double price = intent.getDoubleExtra("price",0);
        float rate = intent.getFloatExtra("rate",0);*/
        String name = intent.getStringExtra("name");
        String scannedon = intent.getStringExtra("scannedon");
        String fullname = intent.getStringExtra("fullname");
        String ic = intent.getStringExtra("ic");
        username = intent.getStringExtra("Username");


        if (intent !=null){

            mActionBar.setTitle(name);
            mName.setText(name);
            mFullname.setText(fullname);
            mScannedon.setText(scannedon);
            mIc.setText(ic);

            //Glide.with(DetailedProductsActivity.this).load(image).into(mImage);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        setResult(RESULT_OK, intent);
        finish();
    }
}
