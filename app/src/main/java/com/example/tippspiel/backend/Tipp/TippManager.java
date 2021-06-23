package com.example.tippspiel.backend.Tipp;

import android.widget.EditText;

import com.example.tippspiel.InternalConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TippManager {
    private static List<Tipper> tipperList = new ArrayList<>();
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

    public static void setTipperName(String tippSpieler) {
        for (Tipper tipper : tipperList) {
            if (tipper.getName().equals(InternalConstants.EmptyStr)){
                tipper.setName(tippSpieler);
                return;
            }

        }
    }

    public static String getJsonString() {
        try {
                JSONArray jsonTipperArr = new JSONArray();
                for (Tipper tipper : tipperList) {
                    JSONArray jsonSpielTippArr = new JSONArray();

                    for (SpielTipp spielTipp: tipper.getSpielTippList()) {
                        if (spielTipp.getErgebnis().equals(InternalConstants.EmptyStr)){
                            continue;
                        }
                        JSONObject jsonSpielTippObject = getJsonSpielTippObject(spielTipp);
                        jsonSpielTippArr.put(jsonSpielTippObject);
                    }
                    JSONObject tipperSpieleObjekt = getJsonTipperSpieleObject(jsonSpielTippArr, InternalConstants.TipperSpiele);
                    jsonTipperArr.put(getJsonTipperObject(tipper));
                    jsonTipperArr.put(tipperSpieleObjekt);
                }
            JSONObject tipperObjekt = getJsonTipperSpieleObject(jsonTipperArr, InternalConstants.Tipper);

            return tipperObjekt.toString();
            } catch ( JSONException e) {
                e.printStackTrace();
            }
            return InternalConstants.EmptyStr;
    }

    private static JSONObject getJsonTipperSpieleObject(JSONArray jsonSpielTippArr, String tipperSpiele) throws JSONException {
        JSONObject tipperSpieleObjekt = new JSONObject();
        tipperSpieleObjekt.put(tipperSpiele, jsonSpielTippArr);
        return tipperSpieleObjekt;
    }

    private static JSONObject getJsonSpielTippObject(SpielTipp spielTipp) throws JSONException {
        JSONObject jsonSpielTippObject = new JSONObject();
        jsonSpielTippObject.put(InternalConstants.TipperSpielId, spielTipp.getSpielId());
        jsonSpielTippObject.put(InternalConstants.TipperSpielErgebnis, spielTipp.getErgebnis());
        jsonSpielTippObject.put(InternalConstants.TipperSpielIsEvaluated, spielTipp.isEvaluated());
        return jsonSpielTippObject;
    }

    private static JSONObject getJsonTipperObject(Tipper tipper) throws JSONException {
        JSONObject jsonTipperObject = new JSONObject();
        jsonTipperObject.put(InternalConstants.TipperId, tipper.getTipperId());
        jsonTipperObject.put(InternalConstants.TipperName, tipper.getName());
        jsonTipperObject.put(InternalConstants.TipperPunkte, tipper.getPunkte());
        return jsonTipperObject;
    }
}
