package com.example.tippspiel.backend.Spiel;

public class Goalgetter {

    public Goalgetter(String name, String minute, boolean isPenalty, boolean isOwngoal){
        setMinute(minute);
    }

    private void setMinute(String minute) {
        int minute1;
        if (minute != null && !minute.equals("null"))
        minute1 = Integer.parseInt(minute);
    }
}
