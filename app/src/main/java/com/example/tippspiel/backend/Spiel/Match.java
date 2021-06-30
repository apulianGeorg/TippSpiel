package com.example.tippspiel.backend.Spiel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Match {
    private int matchid;
    private Team team1;
    private Team team2;
    //TODO: Setzen und abfragen
    private String location;
    private boolean matchIsFinished=false;
    //TODO: Setzen und abfragen
    private String matchTime;

    public Match(int matchId, Team team1, Team team2, String location, String matchTime, boolean matchIsFinished){
        setMatchid(matchId);
        setTeam1(team1);
        setTeam2(team2);
        setLocation(location);
        setMatchTime(matchTime);
        setMatchIsFinished(matchIsFinished);
    }

    public Match(Match correspondingMatch) {
        this(correspondingMatch.getMatchid(),
                new Team(correspondingMatch.team1),
                new Team(correspondingMatch.team2),
                correspondingMatch.getLocation(),
                correspondingMatch.getMatchTime(),
                correspondingMatch.matchIsFinished);
    }

    public int getMatchid() {
        return matchid;
    }

    private void setMatchid(int matchid) {
        this.matchid = matchid;
    }

    public Team getTeam1() {
        return team1;
    }

    private void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    private void setTeam2(Team team2) {
        this.team2 = team2;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    private void setMatchIsFinished(boolean matchIsFinished) {
        this.matchIsFinished = matchIsFinished;
    }

    private void setMatchTime(String matchTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMAN);
        try {
            simpleDateFormat.parse(matchTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.matchTime = matchTime;
    }

    public String getResult() {
        if (matchIsFinished){
            return team1.getGoalsTeam() + " : " + team2.getGoalsTeam();
        } else {
            return "  :  ";
        }
    }

    public boolean isMatchIsFinished() {
        return matchIsFinished;
    }

    public String getLocation() {
        return location;
    }

    public String getMatchTime() {
        return matchTime;
    }
}
