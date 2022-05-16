package com.example.civica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GiornoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giorno);

        Intent i = getIntent();
        Parser p = (Parser)i.getSerializableExtra("parser");
        System.out.println(p.infoGiorno("2022-05-10"));
    }
}