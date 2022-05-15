package EdCivicaTPSI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;

public class Parser {
    private HashMap<String, Integer> mapGiorno;
    private HashMap<String, Integer> mapProvincia;

    public Parser(){
        mapGiorno = new HashMap<>();
        mapProvincia = new HashMap<>();
    }

    public HashMap<String,Integer> getMapGiorno() {
        return this.mapGiorno;
    }

    public HashMap<String,Integer> getMapProvincia() {
        return this.mapProvincia;
    }

    public boolean parseGiorno(int year, int month, int day){
        BufferedReader buffer = null;
        String mese = String.format("%02d", month);  // => "01"
        String giorno = String.format("%02d", day);
        mapGiorno.clear();
        try {
            URL url = new URL("https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-province/dpc-covid19-ita-province-" + year + mese + giorno + ".csv");
            URLConnection connection = url.openConnection();

            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            String line = "";
            String csvSplitBy = ",";
        
            buffer = new BufferedReader(input);
            while ((line = buffer.readLine()) != null) {
                String row[] = line.split(csvSplitBy);
                if(row[0].equals("data") || row[5].equals("In fase di definizione/aggiornamento")){
                    continue;
                }
                mapGiorno.put(row[5], Integer.parseInt(row[9]));
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

    public boolean parseProvincia (String provincia) {
        BufferedReader buffer = null;
        mapProvincia.clear();
        try {
            URL url = new URL("https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-province/dpc-covid19-ita-province.csv");
            URLConnection connection = url.openConnection();

            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            String line = "";
            String csvSplitBy = ",";
        
            buffer = new BufferedReader(input);
            while ((line = buffer.readLine()) != null) {
                String row[] = line.split(csvSplitBy);
                if(row[5].equals(provincia)){
                    String data[] = row[0].split("T");
                    mapProvincia.put(data[0], Integer.parseInt(row[9]));
                }
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

    public static void main(String[] args) throws IOException {
        Parser p = new Parser();
        if(p.parseProvincia("Matera")){
            HashMap<String, Integer> map = p.getMapProvincia();
            System.out.println(map.get("2022-01-01"));
        }
    }
}