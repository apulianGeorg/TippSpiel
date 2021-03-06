package com.example.tippspiel.backend.Spiel;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class TeamInfo {

    private static HashMap<String, Drawable> teamInfoList = new HashMap<>();

    public static void addIfNotExistsTeamInfo(String teamName, String url){
        if (!teamInfoList.containsKey(teamName)){
            teamInfoList.put(teamName, createDrawableFromUrl(url));
        }
    }

    public static Drawable getTeamIcon(String teamName) {
        return teamInfoList.get(teamName);
    }

    //TODO: Background Task
    private static Drawable createDrawableFromUrl(String urlStream) {
        Bitmap x;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlStream).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            x = BitmapFactory.decodeStream(input);
            if (x==null){
                x = tryCreateDrawableWithHttps(urlStream);
            }
            Drawable teamIcon = new BitmapDrawable(Resources.getSystem(), x);
            return teamIcon;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Bitmap tryCreateDrawableWithHttps(String urlStream) throws IOException {
        HttpURLConnection connection;
        InputStream input;
        Bitmap x;
        String urlHttpsStream=urlStream.replace("http", "https");
        connection = (HttpURLConnection) new URL(urlHttpsStream).openConnection();
        connection.connect();
        input = connection.getInputStream();
        x = BitmapFactory.decodeStream(input);
        return x;
    }

}
