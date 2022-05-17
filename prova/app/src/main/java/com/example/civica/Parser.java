package com.example.civica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {
    private ArrayList<String> provincie;
    private ArrayList<String> giorni;
    private LinkedHashMap<String, Integer> mapDownload;

    public Parser(){
        provincie = new ArrayList<>();
        giorni = new ArrayList<>();
        mapDownload = new LinkedHashMap<>();
    }

    public LinkedHashMap<String,Integer> getMapDownload() {
        return this.mapDownload;
    }

    public boolean parseDownload(){
        BufferedReader buffer = null;
        mapDownload.clear();
        try {
            URL url = new URL("https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-province/dpc-covid19-ita-province.csv");
            URLConnection connection = url.openConnection();

            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            String line = "";
            String csvSplitBy = ",";
            
            buffer = new BufferedReader(input);
            while ((line = buffer.readLine()) != null) {
                String row[] = line.split(csvSplitBy);
                if(row[0].equals("data") || row[5].equals("In fase di definizione/aggiornamento") || row[5].equals("Fuori Regione / Provincia Autonoma")){
                    continue;
                }
                if(row[5].equals("L'Aquila")){
                    giorni.add(row[0].split("T")[0]);
                }
                if(row[0].equals("2020-02-24T18:00:00")){
                    provincie.add(row[5]);
                }
                String key = row[5] + "D" + row[0].split("T")[0];
                mapDownload.put(key, Integer.parseInt(row[9]));
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public LinkedHashMap<String, Integer> infoProvincia(String provincia){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        for(int i = 0; i < giorni.size(); i++){
            String key = provincia + "D" + giorni.get(i);
            map.put(giorni.get(i), mapDownload.get(key));
        }
        return map;
    }

    public LinkedHashMap<String, Integer> infoGiorno(String giorno){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        for(int i = 0; i < provincie.size(); i++){
            String key = provincie.get(i) + "D" + giorno;
            map.put(provincie.get(i), mapDownload.get(key));
        }
        return map;
    }

    public String exportString (){
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Integer>> it = mapDownload.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            sb.append(pair.getKey() + "," + pair.getValue() + "\n");
        }
        return sb.toString();
    }

    public boolean importString (String string){
        giorni.clear();
        provincie.clear();
        mapDownload.clear();

        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new StringReader(string));
            String line = "";
            String csvSplitBy = ",";
            while ((line = buffer.readLine()) != null) {
                String row[] = line.split(csvSplitBy);
                String values[] = row[0].split("D");
                mapDownload.put(row[0], Integer.parseInt(row[1]));
                if(!giorni.contains(values[1])){
                    giorni.add(values[1]);
                }
                if(!provincie.contains(values[0])){
                    provincie.add(values[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}