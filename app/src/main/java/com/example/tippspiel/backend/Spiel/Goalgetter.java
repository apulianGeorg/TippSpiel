package com.example.tippspiel.backend.Spiel;

public class Goalgetter {

    //TODO: Noch setzen uns ausgeben
    private int minute;
    private String name;
    private boolean isOwnGoal;
    private boolean isPenalty;

    public Goalgetter(String name, String minute, boolean isOwnGoal, boolean isPenalty){
        this.name=name;
        setMinute(minute);
        this.isOwnGoal=isOwnGoal;
        this.isPenalty= isPenalty;
    }

    private void setMinute(String minute) {

        if (minute != null && !minute.equals("null"))
        this.minute = Integer.parseInt(minute);
    }
}
