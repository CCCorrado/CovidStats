package com.example.civica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Parser p = new Parser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provincia);

        RelativeLayout r = findViewById(R.id.loading);
        r.setVisibility(View.VISIBLE);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    p.parseDownload();
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

        Button giorno = findViewById(R.id.ButtonDay);
        giorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GiornoActivity.class);
                startActivity(i);
            }
        });


    }
}