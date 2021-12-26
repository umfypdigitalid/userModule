package com.fyp.digitalid;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSocket {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        ServerSocket server = new ServerSocket(10100);
        try {
            System.out.println("Server has started on 127.0.0.1:10100.\r\nWaiting for a connection...");
            Socket client = server.accept();
            System.out.println("A client connected.");
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            Scanner s = new Scanner(in, "UTF-8");
            try {
                String data = s.useDelimiter("\\r\\n\\r\\n").next();
                Matcher get = Pattern.compile("^GET").matcher(data);
                if (get.find()) {
                    Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
                    match.find();
                    byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                            + "Connection: Upgrade\r\n"
                            + "Upgrade: websocket\r\n"
                            + "Sec-WebSocket-Accept: "
                            + Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-1").digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes("UTF-8")))
                            + "\r\n\r\n").getBytes("UTF-8");
                    out.write(response, 0, response.length);
                }
                //out.write("**UID: 17132981**".getBytes());
                //out.flush();

                while (true) {
                    byte[] b = new byte[1024];
                    int size = in.read(b);
                    if (size == -1) {
                        continue;
                    }
                    System.out.print(client.getInetAddress()+": ");
                    printByte(decode(b, size));
                }
            } finally {
                s.close();
            }
        } finally {
            server.close();
        }
    }

    public static byte[] decode(byte[] encoded, int size) {
        byte[] decoded = new byte[size - 6];
        byte[] key = new byte[]{encoded[2], encoded[3], encoded[4], encoded[5]};

        for (int i = 0; i < size - 6; i++) {
            decoded[i] = (byte) (encoded[i + 6] ^ key[i & 0x3]);
        }
        return decoded;
    }

    public static void printByte(byte[] message) {
        for (int i = 0; i < message.length; i++) {
            System.out.print((char) message[i]);
        }
        System.out.println("");
    }
}