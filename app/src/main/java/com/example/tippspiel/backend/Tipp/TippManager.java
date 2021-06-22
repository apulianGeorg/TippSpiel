package com.example.tippspiel.backend.Tipp;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class TippManager {
    public static List<Tipper> tipperList = new ArrayList<>();
    private static int tipperId=0;

    public static void neuerTipp(EditText tippSpielerNameEditText, int matchId, String ergebnisTipp) {
        String tippSpielerName = (tippSpielerNameEditText==null) ? "" : tippSpielerNameEditText.getText().toString();

        for (Tipper tipper : tipperList) {
            if (tipper.getName().equals(tippSpielerName)) {
                for (SpielTipp spielTipp : tipper.getSpielTippList()) {
                    if (spielTipp.getSpielId() == matchId) {
                       spielTipp.setErgebnis(ergebnisTipp);
                       return;
                    }
                }
                tipper.addSpielTipp(matchId, ergebnisTipp);
                return;
            }
        }
        tipperId++;
        tipperList.add(new Tipper(tippSpielerName, tipperId, matchId, ergebnisTipp));
    }

    public static String tipperListGet(EditText tippSpielerNameEditText, int matchId) {
        String tippSpielerName = (tippSpielerNameEditText==null) ? "" :
                tippSpielerNameEditText.getText().toString();
        //TODO: Hashmaps
        for (Tipper tipper : tipperList) {
            if (tipper.getName().equals(tippSpielerName)) {
                for (SpielTipp spielTipp : tipper.getSpielTippList()) {
                    if (spielTipp.getSpielId() == matchId) {
                        return spielTipp.getErgebnis();
                    }
                }
            }
        }
        return "";
    }

    public static int getTipperId() {
        return (++tipperId);
    }

    public static void setTipperList(List<Tipper> mapFileToTipperList) {
        tipperList=mapFileToTipperList;
        tipperId=tipperList.size();
    }
}
