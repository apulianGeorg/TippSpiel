package com.example.tippspiel.backend.Spiel;

public class Goalgetter {

    public Goalgetter(String minute){
        setMinute(minute);
    }

    private void setMinute(String minute) {
        int minute1;
        if (minute != null && !minute.equals("null"))
        minute1 = Integer.parseInt(minute);
    }
}
