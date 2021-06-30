package com.example.tippspiel.backend.Tipp;

import java.util.ArrayList;
import java.util.List;

public class Tipper implements Comparable<Tipper>{

    private String name;
    private int tipperId;
    private int points;
    private List<MatchTipp> matchTippList =new ArrayList<>();

    Tipper(String tipperName, int tipperId, int matchId, String tipp) {
        setName(tipperName);
        setTipperId(tipperId);
        points =0;
        matchTippList.add(new MatchTipp(matchId, tipp));
    }

    public Tipper() {
        setName("");
        setTipperId(0);
        points =0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    int getTipperId() {
        return tipperId;
    }

    public void setTipperId(int tipperId) {
        this.tipperId = tipperId;
    }

    public List<MatchTipp> getMatchTippList() {
        return matchTippList;
    }

    public void setMatchTippList(List<MatchTipp> matchTippList) {
        this.matchTippList = matchTippList;
    }

    void addMatchTipp(int matchId, String ergebnisTipp) {
        matchTippList.add(new MatchTipp(matchId, ergebnisTipp));
    }

    @Override
    public int compareTo(Tipper o) {
        if (this.points > o.points){
            return -1;
        }
        if (this.points < o.points){
            return 1;
        }
        return this.name.compareTo(o.name);
    }
}
