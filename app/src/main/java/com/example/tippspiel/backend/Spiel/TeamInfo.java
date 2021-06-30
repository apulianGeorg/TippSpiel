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

    private static Drawable createDrawableFromUrl(String url) {
        Bitmap x;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            x = BitmapFactory.decodeStream(input);
            return new BitmapDrawable(Resources.getSystem(), x);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
