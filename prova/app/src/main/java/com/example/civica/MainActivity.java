package com.example.civica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Parser p = new Parser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout r = findViewById(R.id.loading);
        r.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    p.parseDownload();
                    HashMap map = p.infoGiorno("2020-08-06");
                    System.out.println(map);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            r.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
}