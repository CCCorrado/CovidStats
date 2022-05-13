package EdCivicaTPSI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Parser {
    public Parser(){}

    public String parseUrl(int year, int month, int day){
        BufferedReader buffer = null;
        StringBuilder sb = null;
        String mese = String.format("%02d", month);  // => "01"
        String giorno = String.format("%02d", day);
        try {
            URL url = new URL("https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-province/dpc-covid19-ita-province-" + year + mese + giorno + ".csv");
            URLConnection connection = url.openConnection();

            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            String line = "";
            String csvSplitBy = ",";
        
            sb = new StringBuilder();
            buffer = new BufferedReader(input);
            while ((line = buffer.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();

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

    public static void main(String[] args) throws IOException {
        Parser p = new Parser();
        System.out.println(p.parseUrl(2020, 10, 1));
    }
}