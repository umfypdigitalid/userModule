package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ShowQr extends AppCompatActivity {

    //Button btngenerate;
    //EditText input;
    ImageView output;
    String QRdata,fullname,ic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //input = findViewById(R.id.input);
        //btngenerate = findViewById(R.id.btngenerate);
        output = findViewById(R.id.output);
        fullname="Loh Le Qing";
        ic="990915015426";
        QRdata = new StringBuilder().append(fullname).append("\n").append(ic).toString();
        System.out.println(QRdata);
        //String sText = input.getText().toString().trim();
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
        /*btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname="Loh Le Qing";
                ic="990915015426";
                QRdata = new StringBuilder().append(fullname).append("/n").append(ic).toString();
                System.out.println(QRdata);
                //String sText = input.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(QRdata, BarcodeFormat.QR_CODE, 350, 350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    //Set bitmap on image view
                    output.setImageBitmap(bitmap);
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //hide soft keyboard
                    manager.hideSoftInputFromWindow(input.getApplicationWindowToken(),0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
}