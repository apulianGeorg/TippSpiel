package com.example.tippspiel.basics;

import android.os.AsyncTask;

import com.example.tippspiel.backend.Map.JsonToMatchListMap;
import com.example.tippspiel.backend.Spiel.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyHtmlReader extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        return ReadHtmlPageAsString();
    }

    private String ReadHtmlPageAsString(){
        StringBuilder retStr = new StringBuilder();
        try {
            //TODO: Variable URL
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

    @Override
    protected void onPostExecute(String str) {
    }
}
