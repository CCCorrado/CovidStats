package com.example.civica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    private ArrayList<String> provincie;
    private ArrayList<String> giorni;
    private HashMap<String, Integer> mapDownload;

    public Parser(){
        provincie = new ArrayList<>();
        giorni = new ArrayList<>();
        mapDownload = new HashMap<>();
    }

    public HashMap<String,Integer> getMapDownload() {
        return this.mapDownload;
    }

    public String getLast(){
        BufferedReader buffer = null;
        try {
            // Si ottiene nella variabile last l'ultimo giorno del dataset
            URL url = new URL("https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-province/dpc-covid19-ita-province-latest.csv");
            URLConnection connection = url.openConnection();
            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            buffer = new BufferedReader(input);

            buffer.readLine();
            String line = buffer.readLine();
            String last = line.split(",")[0].split("T")[0];
            return last;
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
        return null;
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

    public HashMap<String, Integer> infoProvincia(String provincia){
        HashMap<String, Integer> map = new HashMap<>();
        for(int i = 0; i < giorni.size(); i++){
            String key = provincia + "D" + giorni.get(i);
            map.put(giorni.get(i), mapDownload.get(key));
        }
        return map;
    }

    public HashMap<String, Integer> infoGiorno(String giorno){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 0; i < provincie.size(); i++){
            String key = provincie.get(i) + "D" + giorno;
            map.put(provincie.get(i), mapDownload.get(key));
        }
        return map;
    }

    public static void main(String[] args) {
        System.out.println("prova");
    }
}