package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ShowQr extends AppCompatActivity {

    ImageView output;
    String QRdata, timestamp,username;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("Username");
        output = findViewById(R.id.output);

        Date date = new Date(System.currentTimeMillis());
        timestamp=formatter.format(date);
        QRdata = bundle.getString("qrdata");
        QRdata = new StringBuilder().append(QRdata).append("\n").append(timestamp).toString();
        System.out.println("showqr:" +QRdata);
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(QRdata, BarcodeFormat.QR_CODE, 350, 350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            //Set bitmap on image view
            output.setImageBitmap(bitmap);
            //InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //hide soft keyboard
            //manager.hideSoftInputFromWindow(input.getApplicationWindowToken(),0);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        setResult(RESULT_OK, intent);
        finish();
    }
    //todo expire in 30 min
    //https://wee.example.com/expire.php?code=abcd1234876ce067e
}