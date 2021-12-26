package com.fyp.digitalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class UserId extends BaseActivity {

    BufferedInputStream is;
    String line = null;
    String userid = null;
    TextView txtuserid;
    String username;
    private static final String SECRET_KEY = "my_super_secret_key_ho_ho_ho";
    private static final String SALT = "ssshhhhhhhhhhh!!!!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_id);
        this.username = getIntent().getStringExtra("Username");
        System.out.println("username: "+ username);
//        userid=getUserID(username);
        getUserID();
//        txtuserid = findViewById(R.id.txtuserid);
//        System.out.println("user id: "+userid);
//        txtuserid.setText(userid);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

    }

    private void getUserID() {
//
//        System.out.println("Username: "+username);
//        String showURL = "http://192.168.0.118:8080/digitalid/userid.php?username="+username;
//        try{
//
//            URL url = new URL(showURL);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("GET");
//            is = new BufferedInputStream(con.getInputStream());
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        //content
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//            while((line=br.readLine())!=null){
//                sb.append(line+"\n");
//            }
//            is.close();
//            userid = sb.toString();
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String showURL = "http://192.168.0.118:8080/digitalid/userid.php?username="+username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, showURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Result: "+response);
                userid=response;
                txtuserid = findViewById(R.id.txtuserid);
                System.out.println("user id: "+userid);
                txtuserid.setText(userid);
                try {
                    createkey();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);




        /*String showURL = "http://192.168.0.198:8080/digitalid/userid.php?username="+username;
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
            createkey();

        }catch (Exception ex){
            ex.printStackTrace();
        }*/

//        return userid;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Username", username);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void createkey() throws Exception{

//        /* Derive the key, given password and salt. */
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
//        SecretKey tmp = factory.generateSecret(spec);
//        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
//        System.out.println("Private Key: ");
//
//        /* Encrypt the message. */
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, secret);
//        AlgorithmParameters params = cipher.getParameters();
//        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
//        byte[] ciphertext = cipher.doFinal("Hello, World!".getBytes(StandardCharsets.UTF_8));
//
//        /* Decrypt the message, given derived key and initialization vector. */
//        Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher2.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
//        String plaintext = new String(cipher2.doFinal(ciphertext), StandardCharsets.UTF_8);
//        System.out.println(plaintext);

        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //Initializing the key pair generator
        keyPairGen.initialize(2048);
        //Generating the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        System.out.println("Private Key: "+priv);
        System.out.println("Public Key: "+pub);

        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        //Initializing a Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
        //Adding data to the cipher
        byte[] input = "Welcome to Tutorialspoint".getBytes();
        cipher.update(input);

        //encrypting the data
        byte[] cipherText = cipher.doFinal();
        System.out.println(new String(cipherText, "UTF8"));

        //Initializing the same cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
        //Decrypting the text
        byte[] decipheredText = cipher.doFinal(cipherText);
        System.out.println(new String(decipheredText));

    }

        /*Key key;
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance("AES");
            generator.init(256, rand);
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/


}