package com.example.tippspiel.backend.Tipp;

import java.util.ArrayList;
import java.util.List;

public class Tipper {
    private String name;
    private int tipperId;
    private int punkte;
    private List<SpielTipp> spielTippList=new ArrayList<>();

    public Tipper(String tippSpielerName, int tipperId, int matchId, String ergebnisTipp) {
        setName(tippSpielerName);
        setTipperId(tipperId);
        punkte=0;
        spielTippList.add(new SpielTipp(matchId, ergebnisTipp));
    }

    public Tipper() {
        setName("");
        setTipperId(0);
        punkte=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getTipperId() {
        return tipperId;
    }

    public void setTipperId(int tipperId) {
        this.tipperId = tipperId;
    }

    public List<SpielTipp> getSpielTippList() {
        return spielTippList;
    }

    public void setSpielTippList(List<SpielTipp> spielTippList) {
        this.spielTippList = spielTippList;
    }

    public void addSpielTipp(int matchId, String ergebnisTipp) {
        spielTippList.add(new SpielTipp(matchId, ergebnisTipp));
    }
}
