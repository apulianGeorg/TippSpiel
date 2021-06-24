package com.example.tippspiel.backend.Spiel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Spiel {
    private int matchid;
    private Team team1;
    private Team team2;
    private String location;
    private boolean matchIsFinished=false;
    private String gameTime;

    public Spiel(int matchId, Team team1, Team team2, String location, String gameTime, boolean matchIsFinished){
        setMatchid(matchId);
        setTeam1(team1);
        setTeam2(team2);
        setLocation(location);
        setGameTime(gameTime);
        setMatchIsFinished(matchIsFinished);
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

    private void setGameTime(String gameTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date  = simpleDateFormat.parse(gameTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.gameTime = gameTime;
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
}
