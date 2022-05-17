package com.example.civica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
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

        ArrayList<String> regioni = new ArrayList<String>();
        regioni.add("Regione");
        regioni.add("Abruzzo");
        regioni.add("Basilicata");
        regioni.add("Calabria");
        regioni.add("Campania");
        regioni.add("Emilia Romagna");
        regioni.add("Friuli Venezia Giulia");
        regioni.add("Lazio");
        regioni.add("Liguria");
        regioni.add("Lombardia");
        regioni.add("Marche");
        regioni.add("Molise");
        regioni.add("Piemonte");
        regioni.add("Puglia");
        regioni.add("Sardegna");
        regioni.add("Sicilia");
        regioni.add("Toscana");
        regioni.add("Umbria");
        regioni.add("Valle d'Aosta");
        regioni.add("Veneto");

        ArrayList<String> province_Abruzzo = new ArrayList<String>();
        province_Abruzzo.add("L'Aquila");
        province_Abruzzo.add("Teramo");
        province_Abruzzo.add("Pescara");
        province_Abruzzo.add("Chieti");

        ArrayList<String> province_Basilicata = new ArrayList<String>();
        province_Basilicata.add("Potenza");
        province_Basilicata.add("Matera");

        ArrayList<String> province_Calabria = new ArrayList<String>();
        province_Calabria.add("Cosenza");
        province_Calabria.add("Catanzaro");
        province_Calabria.add("Reggio di Calabria");
        province_Calabria.add("Crotone");
        province_Calabria.add("Vibo Valentia");

        ArrayList<String> province_Campania = new ArrayList<String>();
        province_Campania.add("Caserta");
        province_Campania.add("Benevento");
        province_Campania.add("Napoli");
        province_Campania.add("Avellino");
        province_Campania.add("Salerno");

        ArrayList<String> province_Emilia_Romagna = new ArrayList<String>();
        province_Emilia_Romagna.add("Piacenza");
        province_Emilia_Romagna.add("Parma");
        province_Emilia_Romagna.add("Reggio nell'Emilia");
        province_Emilia_Romagna.add("Modena");
        province_Emilia_Romagna.add("Bologna");
        province_Emilia_Romagna.add("Ferrara");
        province_Emilia_Romagna.add("Ravenna");
        province_Emilia_Romagna.add("Forlì-Cesena");
        province_Emilia_Romagna.add("Rimini");

        ArrayList<String> province_Friuli_Venezia_Giulia = new ArrayList<String>();
        province_Friuli_Venezia_Giulia.add("Udine");
        province_Friuli_Venezia_Giulia.add("Gorizia");
        province_Friuli_Venezia_Giulia.add("Trieste");
        province_Friuli_Venezia_Giulia.add("Pordenone");

        ArrayList<String> province_Lazio = new ArrayList<String>();
        province_Lazio.add("Viterbo");
        province_Lazio.add("Rieti");
        province_Lazio.add("Roma");
        province_Lazio.add("Latina");
        province_Lazio.add("Frosinone");

        ArrayList<String> province_Liguria = new ArrayList<String>();
        province_Liguria.add("Imperia");
        province_Liguria.add("Savona");
        province_Liguria.add("Genova");
        province_Liguria.add("La Spezia");

        ArrayList<String> province_Lombardia = new ArrayList<String>();
        province_Lombardia.add("Varese");
        province_Lombardia.add("Como");
        province_Lombardia.add("Sondrio");
        province_Lombardia.add("Milano");
        province_Lombardia.add("Bergamo");
        province_Lombardia.add("Brescia");
        province_Lombardia.add("Pavia");
        province_Lombardia.add("Cremona");
        province_Lombardia.add("Mantova");
        province_Lombardia.add("Lecco");
        province_Lombardia.add("Lodi");
        province_Lombardia.add("Monza e della Brianza");

        ArrayList<String> province_Marche = new ArrayList<String>();
        province_Marche.add("Pesaro e Urbino");
        province_Marche.add("Ancona");
        province_Marche.add("Macerata");
        province_Marche.add("Ascoli Piceno");
        province_Marche.add("Fermo");

        ArrayList<String> province_Molise = new ArrayList<String>();
        province_Molise.add("Campobasso");
        province_Molise.add("Isernia");

        ArrayList<String> province_Piemonte = new ArrayList<String>();
        province_Piemonte.add("Torino");
        province_Piemonte.add("Vercelli");
        province_Piemonte.add("Novara");
        province_Piemonte.add("Cuneo");
        province_Piemonte.add("Asti");
        province_Piemonte.add("Alessandria");
        province_Piemonte.add("Biella");
        province_Piemonte.add("Verbano-Cusio-Ossola");

        ArrayList<String> province_Puglia = new ArrayList<String>();
        province_Puglia.add("Foggia");
        province_Puglia.add("Bari");
        province_Puglia.add("Taranto");
        province_Puglia.add("Brindisi");
        province_Puglia.add("Lecce");
        province_Puglia.add("Barletta-Andria-Trani");

        ArrayList<String> province_Sardegna = new ArrayList<String>();
        province_Sardegna.add("Sassari");
        province_Sardegna.add("Nuoro");
        province_Sardegna.add("Cagliari");
        province_Sardegna.add("Oristano");
        province_Sardegna.add("Sud Sardegna");

        ArrayList<String> province_Sicilia = new ArrayList<String>();
        province_Sicilia.add("Trapani");
        province_Sicilia.add("Palermo");
        province_Sicilia.add("Messina");
        province_Sicilia.add("Agrigento");
        province_Sicilia.add("Caltanissetta");
        province_Sicilia.add("Enna");
        province_Sicilia.add("Catania");
        province_Sicilia.add("Ragusa");
        province_Sicilia.add("Siracusa");

        ArrayList<String> province_Toscana = new ArrayList<String>();
        province_Toscana.add("Massa Carrara");
        province_Toscana.add("Lucca");
        province_Toscana.add("Pistoia");
        province_Toscana.add("Firenze");
        province_Toscana.add("Livorno");
        province_Toscana.add("Pisa");
        province_Toscana.add("Arezzo");
        province_Toscana.add("Siena");
        province_Toscana.add("Grosseto");
        province_Toscana.add("Prato");

        ArrayList<String> province_Umbria = new ArrayList<String>();
        province_Umbria.add("Perugia");
        province_Umbria.add("Terni");

        ArrayList<String> province_Valle_d_aosta = new ArrayList<String>();
        province_Valle_d_aosta.add("Aosta");

        ArrayList<String> province_Veneto = new ArrayList<String>();
        province_Veneto.add("Verona");
        province_Veneto.add("Vicenza");
        province_Veneto.add("Belluno");
        province_Veneto.add("Treviso");
        province_Veneto.add("Venezia");
        province_Veneto.add("Padova");
        province_Veneto.add("Rovigo");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regioni);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner REGIONI = (Spinner) findViewById(R.id.Region);
        REGIONI.setAdapter(adapter);
        REGIONI.setVisibility(View.VISIBLE);


        REGIONI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String region = adapterView.getItemAtPosition(i).toString();
                System.out.println(region);

                Spinner PROVINCE = (Spinner) findViewById(R.id.Province);
                switch (region) {
                    case "Abruzzo":
                        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Abruzzo);
                        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp1);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Basilicata":
                        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Basilicata);
                        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp2);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Calabria":
                        ArrayAdapter<String> adp3 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Calabria);
                        adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp3);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Campania":
                        ArrayAdapter<String> adp4 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Campania);
                        adp4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp4);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Emilia Romagna":
                        ArrayAdapter<String> adp5 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Emilia_Romagna);
                        adp5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp5);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Friuli Venezia Giulia":
                        ArrayAdapter<String> adp6 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Friuli_Venezia_Giulia);
                        adp6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp6);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Lazio":
                        ArrayAdapter<String> adp7 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Lazio);
                        adp7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp7);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Liguria":
                        ArrayAdapter<String> adp8 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Liguria);
                        adp8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp8);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Lombardia":
                        ArrayAdapter<String> adp9 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Lombardia);
                        adp9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp9);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Marche":
                        ArrayAdapter<String> adp10 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Marche);
                        adp10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp10);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Molise":
                        ArrayAdapter<String> adp11 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Molise);
                        adp11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp11);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Piemonte":
                        ArrayAdapter<String> adp18 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Piemonte);
                        adp18.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp18);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Puglia":
                        ArrayAdapter<String> adp12 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Puglia);
                        adp12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp12);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Sardegna":
                        ArrayAdapter<String> adp13 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Sardegna);
                        adp13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp13);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Sicilia":
                        ArrayAdapter<String> adp14 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Sicilia);
                        adp14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp14);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Toscana":
                        ArrayAdapter<String> adp15 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Toscana);
                        adp15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp15);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Umbria":
                        ArrayAdapter<String> adp19 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Umbria);
                        adp19.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp19);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Valle d'Aosta":
                        ArrayAdapter<String> adp16 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Valle_d_aosta);
                        adp16.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp16);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                    case "Veneto":
                        ArrayAdapter<String> adp17 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, province_Veneto);
                        adp17.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        PROVINCE.setAdapter(adp17);
                        PROVINCE.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
                if(spinner != null && spinner.getSelectedItem() != null) {
                    System.out.println(spinner.getSelectedItem());
                    String provincia = spinner.getSelectedItem().toString();
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