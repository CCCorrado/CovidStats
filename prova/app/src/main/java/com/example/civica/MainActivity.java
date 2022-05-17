package com.example.civica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    private Parser p = null;
    private String data = null;

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
                    Intent intent = getIntent();
                    data = intent.getStringExtra("key");
                    p = new Parser();
                    if(data == null || !data.equals("true")){
                        p.parseDownload();
                    } else {
                        String importString = readFromFile(getApplicationContext());
                        p.importString(importString);
                    }
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
                if(data == null || !data.equals("true")){
                    writeToFile(p.exportString());
                }
                Bundle ePzl= new Bundle();
                ePzl.putString("key", "true");

                Intent i = new Intent(MainActivity.this,GiornoActivity.class);
                i.putExtras(ePzl);
                startActivity(i);
            }
        });

        Button select = findViewById(R.id.Search);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView result = findViewById(R.id.View);
                result.setMovementMethod(new ScrollingMovementMethod());
                Spinner spinner = findViewById(R.id.Province);
                if(spinner.getSelectedItem() == null) {
                    String provincia = "Livorno";//spinner.getSelectedItem().toString();
                    LinkedHashMap<String, Integer> map = p.infoProvincia(provincia);

                    StringBuilder sb = new StringBuilder();
                    for (String key : map.keySet()) {
                        String tmp = sb.toString();
                        sb = new StringBuilder();
                        sb.append(key).append("\t:\t").append(map.get(key)).append("\n").append(tmp);
                    }
                    result.setText(sb.toString());
                } else {
                    result.setText("\n\nInserire una provincia corretta");
                }
            }
        });
    }

    private boolean writeToFile(String data){
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, "config.csv"));
            writer.write(data.getBytes());
            writer.close();
            Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.csv");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString + "\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        return ret;
    }
}