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
    private HashMap<String, Integer> mapDownload;

    public Parser(){
        mapGiorno = new HashMap<>();
        mapProvincia = new HashMap<>();
        mapDownload = new HashMap<>();
    }

    public HashMap<String,Integer> getMapGiorno() {
        return this.mapGiorno;
    }

    public HashMap<String,Integer> getMapProvincia() {
        return this.mapProvincia;
    }

    public HashMap<String,Integer> getMapDownload() {
        return this.mapDownload;
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

    public String getLast(){
        BufferedReader buffer = null;
        mapProvincia.clear();
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
                String key = row[5] + "D" + row[0].split("T")[0];
                //System.out.println(key);
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

    public static void main(String[] args) throws IOException {
        Parser p = new Parser();
        System.out.println(p.parseDownload());
        HashMap<String, Integer> map = p.getMapDownload();
        String key = "LivornoD2022-05-05";
        System.out.println(map.get(key));
    }
}