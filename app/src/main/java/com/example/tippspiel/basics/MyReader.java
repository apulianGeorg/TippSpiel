package com.example.tippspiel.basics;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MyReader {

    public static String ReadHtmlPageAsString(String urlStr){
        StringBuilder retStr = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            InputStream input = url.openStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while((line= reader.readLine())!=null){
                retStr.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr.toString();
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /*
    private static HashMap<Integer, String> getSpielTipps() {
        HashMap<Integer, String> spielTipps =new HashMap<>();

        //Creating a JSONParser object
        JSONParser jsonParser = new JSONParser();
        try {
            //Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("E:/sample.json"));
            String id = (String) jsonObject.get("ID");
            String first_name = (String) jsonObject.get("First_Name");
            String last_name = (String) jsonObject.get("Last_Name");
            String date_of_birth = (String) jsonObject.get("Date_Of_Birth");
            String place_of_birth = (String) jsonObject.get("Place_Of_Birth");
            String country = (String) jsonObject.get("Country");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        return spielTipps;

        }
    */

}
