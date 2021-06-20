package com.example.tippspiel.backend;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String teamName;
    private String teamIcon;
    private int goalsTeam;
    private List<Goalgetter> goalgetters = new ArrayList<>();

    public Team (String teamName, String teamIcon){
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

    public String getTeamIcon() {
        return teamIcon;
    }

    public void setTeamIcon(String teamIcon) {
        this.teamIcon = teamIcon;
    }

    public String getGoalsTeam() {
        return String.valueOf(goalsTeam);
    }

    public void setGoalsTeam(int goalsTeam) {
        this.goalsTeam = goalsTeam;
    }
}
