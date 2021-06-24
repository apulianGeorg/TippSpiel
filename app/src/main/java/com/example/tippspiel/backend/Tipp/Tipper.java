package com.example.tippspiel.backend.Tipp;

import java.util.ArrayList;
import java.util.List;

public class Tipper implements Comparable<Tipper>{
    private String name;
    private int tipperId;
    private int punkte;
    private List<SpielTipp> spielTippList=new ArrayList<>();

    Tipper(String tippSpielerName, int tipperId, int matchId, String ergebnisTipp) {
        setName(tippSpielerName);
        setTipperId(tipperId);
        punkte=0;
        spielTippList.add(new SpielTipp(matchId, ergebnisTipp));
    }

    Tipper() {
        setName("");
        setTipperId(0);
        punkte=0;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getPunkte() {
        return punkte;
    }

    void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    int getTipperId() {
        return tipperId;
    }

    void setTipperId(int tipperId) {
        this.tipperId = tipperId;
    }

    List<SpielTipp> getSpielTippList() {
        return spielTippList;
    }

    void setSpielTippList(List<SpielTipp> spielTippList) {
        this.spielTippList = spielTippList;
    }

    void addSpielTipp(int matchId, String ergebnisTipp) {
        spielTippList.add(new SpielTipp(matchId, ergebnisTipp));
    }

    @Override
    public int compareTo(Tipper o) {
        if (this.punkte > o.punkte){
            return -1;
        }
        if (this.punkte < o.punkte){
            return 1;
        }
        return this.name.compareTo(o.name);
    }
}
