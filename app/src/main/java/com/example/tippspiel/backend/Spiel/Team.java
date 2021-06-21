package com.example.tippspiel.backend.Spiel;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName;
    private Drawable teamIcon;
    private int goalsTeam;
    private List<Goalgetter> goalgetters = new ArrayList<>();

    public Team (String teamName, Drawable teamIcon){
        setTeamName(teamName);
        setTeamIcon(teamIcon);
    }
    public void addGoalgetters(List<Goalgetter> goalgetters){
        if (goalgetters==null){
            goalsTeam=0;
        } else {
            this.goalgetters=goalgetters;
            goalsTeam=goalgetters.size();
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Drawable getTeamIcon() {
        return teamIcon;
    }

    public void setTeamIcon(Drawable teamIcon) {
        this.teamIcon = teamIcon;
    }

    public String getGoalsTeam() {
        return String.valueOf(goalsTeam);
    }

    public void setGoalsTeam(int goalsTeam) {
        this.goalsTeam = goalsTeam;
    }
}
