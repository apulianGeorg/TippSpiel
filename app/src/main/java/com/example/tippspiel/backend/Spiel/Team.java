package com.example.tippspiel.backend.Spiel;

import android.graphics.drawable.Drawable;

import com.example.tippspiel.InternalConstants;

import java.util.List;

public class Team {
    private String teamName;
    private int goalsTeam;

    public Team (String teamName, String teamIconUrl){
        this.teamName=teamName;
        TeamInfo.addIfNotExistsTeamInfo(teamName, teamIconUrl);
    }

    public Team(Team team) {
        this(team.teamName, InternalConstants.EmptyStr);
    }

    public void addGoalgetters(List<Goalgetter> goalgetters){
        if (goalgetters==null){
            goalsTeam=0;
        } else {
            goalsTeam=goalgetters.size();
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public int getGoalsTeam() {
        return goalsTeam;
    }

    public void setGoalsTeam(int goalsTeam) {
        this.goalsTeam = goalsTeam;
    }

    public Drawable getTeamIcon(){
        return TeamInfo.getTeamIcon(teamName);
    }

}
