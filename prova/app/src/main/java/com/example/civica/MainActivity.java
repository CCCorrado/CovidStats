package com.example.civica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    System.out.println("lol");
                    EdCivicaTPSI.Parser p = new EdCivicaTPSI.Parser();
                    System.out.println(p.parseDownload());
                    HashMap<String, Integer> map = p.getMapDownload();
                    System.out.println(map.keySet().size());
                    System.out.println(map.get("LivornoD2022-05-15"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
}