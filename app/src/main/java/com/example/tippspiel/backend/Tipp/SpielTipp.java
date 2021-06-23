package com.example.tippspiel.backend.Tipp;

class SpielTipp {
    private String ergebnis;
    private boolean isEvaluated;
    private int spielId;
    private Integer toreTeam1;
    private Integer toreTeam2;

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

    public int getToreTeam1() {
        if (toreTeam1==null) {
            String[] torArray = ergebnis.split(":");
            if (torArray.length != 2) {
                throw new ArrayIndexOutOfBoundsException("Tore aus " + ergebnis + " nicht ermittelbar");
            }
            toreTeam1= Integer.valueOf(torArray[0]);
            toreTeam2= Integer.valueOf(torArray[1]);
        }
        return toreTeam1;
    }

    public Integer getToreTeam2() {
        return toreTeam2;
    }
}
