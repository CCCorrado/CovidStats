package com.example.civica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class GiornoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateText;
    private Parser p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giorno);
        dateText = findViewById(R.id.View);


        String importString = readFromFile(getApplicationContext());
        p = new Parser();

        if(importString == null || importString.equals("")) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        else {
            p.importString(importString);
        }


        findViewById(R.id.Select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        Button province = findViewById(R.id.ButtonProvince);
        province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle ePzl= new Bundle();
                ePzl.putString("key", "true");

                Intent i = new Intent(GiornoActivity.this,MainActivity.class);
                i.putExtras(ePzl);
                startActivity(i);
            }
        });
    }

    private void showDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month += 1;
        String date = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        LinkedHashMap<String, Integer> map = p.infoGiorno(date);
        if(map == null){
            dateText.setText("Data selezionata errata");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            String tmp = sb.toString();
            sb = new StringBuilder();
            sb.append(key).append("\t:\t").append(map.get(key)).append("\n").append(tmp);
        }
        dateText.setText(sb.toString());
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