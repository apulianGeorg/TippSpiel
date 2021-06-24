package com.example.tippspiel.backend.Tipp;

class SpielTipp {
    private String ergebnis;
    private boolean isEvaluated;
    private int spielId;
    private Integer toreTeam1;
    private Integer toreTeam2;
    private int spielTippPunkte;

    public SpielTipp(int spielId, String ergebnis){
        setErgebnis(ergebnis);
        setSpielId(spielId);
        isEvaluated=false;
    }

    public SpielTipp() {
        this(0,"");
    }

    public String getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(String ergebnis) {
        this.ergebnis = ergebnis.trim()
                .replace("-",":")
                .replace(" ",":");
    }

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(boolean evaluated) {
        isEvaluated = evaluated;
    }

    public int getSpielId() {
        return spielId;
    }

    public void setSpielId(int spielId) {
        this.spielId = spielId;
    }

    public Integer getToreTeam1() {
        if (toreTeam1 == null)  {
            setTore();
        }
        return toreTeam1;
    }

    public Integer getToreTeam2() {
        if (toreTeam2 == null)  {
            setTore();
        }
        return toreTeam2;
    }

    private void setTore() {
        String[] arr = ergebnis.split(":");
        toreTeam1= Integer.valueOf(arr[0]);
        toreTeam2= Integer.valueOf(arr[1]);
    }

    public int getSpielTippPunkte() {
        return spielTippPunkte;
    }

    public void setSpielTippPunkte(int spielTippPunkte) {
        this.spielTippPunkte = spielTippPunkte;
    }
}
