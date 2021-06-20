package com.example.tippspiel.backend;

class Goalgetter {
    private String name;
    private int minute;
    private boolean isPenalty;
    private boolean isOwngoal;

    public Goalgetter(String name, String minute, boolean isPenalty, boolean isOwngoal){
        this.name=name;
        setMinute(minute);
        this.isOwngoal=isOwngoal;
        this.isPenalty=isPenalty;
    }

    private void setMinute(String minute) {
        if (minute != null && !minute.equals("null"))
        this.minute= Integer.parseInt(minute);
    }
}
