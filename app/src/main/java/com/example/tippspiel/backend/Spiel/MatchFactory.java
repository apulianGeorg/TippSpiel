package com.example.tippspiel.backend.Spiel;

import com.example.tippspiel.backend.Map.JsonToMatchListMap;
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
            String jsonStr = new MyHtmlReader().execute().get();
            matchList = JsonToMatchListMap.mapJsonToMatchList(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
