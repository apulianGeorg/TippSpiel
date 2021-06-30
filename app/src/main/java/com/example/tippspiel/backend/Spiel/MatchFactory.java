package com.example.tippspiel.backend.Spiel;

import com.example.tippspiel.basics.MyHtmlReader;

import java.util.ArrayList;

public class MatchFactory {

    private static ArrayList<Match> matchList;

    public static ArrayList<Match> getMatches() {
        if (matchList ==null){
            callXmlParser();
        }
        return matchList;
    }

    private static void callXmlParser(){
        try {
            matchList = new MyHtmlReader().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
