package com.example.tippspiel.backend.Spiel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Spiel {
    private int matchid;
    private Team team1;
    private Team team2;
    private String location;
    private boolean matchIsFinished;
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

    public void setMatchid(int matchid) {
        this.matchid = matchid;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isMatchIsFinished() {
        return matchIsFinished;
    }

    public void setMatchIsFinished(boolean matchIsFinished) {
        this.matchIsFinished = matchIsFinished;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
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
}
