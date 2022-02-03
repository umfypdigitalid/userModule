package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class ShowQr extends BaseActivity {

    ImageView output;
    TextView refresh;
    Dialog myDialog;
    Button btnRefresh;
    Boolean run = true;

    String QRdata, timestamp, username;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    Handler timerHandler = new Handler();
    final Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
//            long millis = System.currentTimeMillis() - startTime;
//            int seconds = (int) (millis / 1000);
//            int minutes = seconds / 60;
//            seconds = seconds % 60;

//            timerTextView.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 30000);
            //Toast.makeText(getApplicationContext(), "run", Toast.LENGTH_SHORT).show();

            btnRefresh.setVisibility(View.VISIBLE);
            output.setVisibility(View.GONE);
            timerHandler.removeCallbacks(timerRunnable);
//            refreshQR();
        }
    };

    public void refreshQrClikced(View v){
        genQR();
        btnRefresh.setVisibility(View.GONE);
        output.setVisibility(View.VISIBLE);
        timerHandler.postDelayed(timerRunnable, 30000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);
        myDialog = new Dialog(this);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("Username");
        output = findViewById(R.id.output);
        refresh = findViewById(R.id.refresh);
        btnRefresh = findViewById(R.id.btnrefresh);

        this.QRdata = bundle.getString("qrdata");
//        generateQR();

        genQR();

        timerHandler.postDelayed(timerRunnable, 30000);
        //Toast.makeText(this, "oncreate", Toast.LENGTH_SHORT).show();
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                refreshQR();
//            }
//        }, 0, 30000);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        setResult(RESULT_OK, intent);
        finish();
    }
    public void genQR(){
        Date date = new Date(System.currentTimeMillis());
        this.timestamp=formatter.format(date);

//        QRdata = new StringBuilder().append(QRdata).append("\n").append(timestamp).toString();
        String qrTemp = new StringBuilder().append(QRdata).append("\n").append(timestamp).toString();
        //QRdata = new StringBuilder().append(QRdata).append("\n").append(formatter.format(date)).toString();
        System.out.println("showqr:" + qrTemp);
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(qrTemp, BarcodeFormat.QR_CODE, 350, 350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            //Set bitmap on image view
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    output.setImageBitmap(bitmap);
                }
            });
            //InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //hide soft keyboard
            //manager.hideSoftInputFromWindow(input.getApplicationWindowToken(),0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

   /* public void generateQR(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date(System.currentTimeMillis());
                timestamp=formatter.format(date);

                QRdata = new StringBuilder().append(QRdata).append("\n").append(timestamp).toString();
                System.out.println("showqr:" + QRdata);
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(QRdata, BarcodeFormat.QR_CODE, 350, 350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    //Set bitmap on image view
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(run) {
                                output.setImageBitmap(bitmap);
                            }else{
                                refreshQR();
                            }
                        }
                    });
                    //InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //hide soft keyboard
                    //manager.hideSoftInputFromWindow(input.getApplicationWindowToken(),0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                *//*new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        refresh.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        refresh.setText("done!");
                    }

                }.start();*//*
                run = false;
            }
        }, 0, 30000);
    }*/

    /*public void refreshQR() {
        timerHandler.removeCallbacks(timerRunnable);  //when clicked close btn
        Button refresh;
        TextView close;
        myDialog.setContentView(R.layout.refresh);
        close = (TextView)myDialog.findViewById(R.id.close);
        refresh = (Button)myDialog.findViewById(R.id.refreshQR);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                onBackPressed();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQR();
                timerHandler.postDelayed(timerRunnable, 10000);
            }
        });
        myDialog.show();
    }*/
}