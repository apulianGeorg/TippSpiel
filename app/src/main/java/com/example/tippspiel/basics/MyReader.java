package com.example.tippspiel.basics;

import com.example.tippspiel.InternalConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MyReader {

    static String ReadHtmlPageAsString(){
        StringBuilder retStr = new StringBuilder();
        try {
            URL url = new URL("https://www.openligadb.de/api/getmatchdata/uefa-em-2020");
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

    public static  String readFile(){
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/download");
        File file = new File(dir, InternalConstants.TippFile);
        try {
            FileReader fileReader= new FileReader(file);
            char[] chars = new char[(int) file.length()];
            final int anzChars = fileReader.read(chars);
            return new String(chars);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return InternalConstants.EmptyStr;
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
