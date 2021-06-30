package com.example.tippspiel.backend.Tipp;

public class MatchTipp {
    private String result;
    private boolean isEvaluated;
    private int matchId;
    private Integer goalsTeam1;
    private Integer goalsTeam2;
    private int matchTippPoints;

    MatchTipp(int matchId, String result){
        setResult(result);
        setMatchId(matchId);
        isEvaluated=false;
    }

    public MatchTipp() {
        this(0,"");
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result.trim()
                .replace("-",":")
                .replace(" ",":");
        if (this.result.length()>0){
            try{
                setTore();
            } catch (IndexOutOfBoundsException e) {
                //TODO: Sauberer strukturieren und dann
                //TODO die Exception weitergeben an die Oberfläche, wenn dort jmd. Blödsinn macht
            }
        }
    }


    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(boolean evaluated) {
        isEvaluated = evaluated;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Integer getGoalsTeam1() {
        return goalsTeam1;
    }

    public Integer getGoalsTeam2() {
        return goalsTeam2;
    }

    private void setTore() throws ArrayIndexOutOfBoundsException{
        String[] arr = result.split(":");
        goalsTeam1 = Integer.valueOf(arr[0]);
        goalsTeam2 = Integer.valueOf(arr[1]);
        if (arr.length > 2){
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public int getMatchTippPoints() {
        return matchTippPoints;
    }

    public void setMatchTippPoints(int matchTippPoints) {
        this.matchTippPoints = matchTippPoints;
    }
}
