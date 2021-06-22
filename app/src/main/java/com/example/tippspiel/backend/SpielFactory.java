package com.example.tippspiel.backend;

import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.basics.XmlReader;

import java.util.ArrayList;

public class SpielFactory {

    static ArrayList<Spiel> spieleListe;

    public static ArrayList<Spiel> getSpiele() {
        if (spieleListe==null){
            callXmlParser();
        }
        return spieleListe;
    }

    private static void callXmlParser(){
        try {
            spieleListe = new XmlReader().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
