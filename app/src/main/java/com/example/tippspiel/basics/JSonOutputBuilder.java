package com.example.tippspiel.basics;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.backend.Tipp.MatchTipp;
import com.example.tippspiel.backend.Tipp.Tipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JSonOutputBuilder {
    public static String getJsonString(List<Tipper> tipperListForJson) {
        try {
            JSONArray jsonTipperArr = new JSONArray();
            for (Tipper tipper : tipperListForJson) {
                JSONArray jsonMatchTippArr = new JSONArray();

                for (MatchTipp matchTipp: tipper.getMatchTippList()) {
                    JSONObject jsonMatchTippObject = getJsonMatchTippObject(matchTipp);
                    jsonMatchTippArr.put(jsonMatchTippObject);
                }
                jsonTipperArr.put(getJsonTipperObject(tipper, jsonMatchTippArr));
            }
            JSONObject tipperObjekt = getJsonTipperObject(jsonTipperArr);

            return tipperObjekt.toString();
        } catch ( JSONException e) {
            e.printStackTrace();
        }
        return InternalConstants.EmptyStr;
    }

    private static JSONObject getJsonTipperObject(JSONArray jsonTippArr) throws JSONException {
        JSONObject tipperObjekt = new JSONObject();
        tipperObjekt.put(InternalConstants.Tipper, jsonTippArr);
        return tipperObjekt;
    }

    private static JSONObject getJsonMatchTippObject(MatchTipp matchTipp) throws JSONException {
        JSONObject jsonMatchTippObjekt = new JSONObject();
        jsonMatchTippObjekt.put(InternalConstants.TipperSpielId, matchTipp.getMatchId());
        jsonMatchTippObjekt.put(InternalConstants.TipperSpielErgebnis, matchTipp.getResult());
        jsonMatchTippObjekt.put(InternalConstants.TipperSpielPunkte, matchTipp.getMatchTippPoints());
        jsonMatchTippObjekt.put(InternalConstants.TipperSpielIsEvaluated, matchTipp.isEvaluated());
        return jsonMatchTippObjekt;
    }

    private static JSONObject getJsonTipperObject(Tipper tipper, JSONArray jsonTippArr) throws JSONException {
        JSONObject jsonTipperObject = new JSONObject();
        jsonTipperObject.put(InternalConstants.TipperId, tipper.getTipperId());
        jsonTipperObject.put(InternalConstants.TipperName, tipper.getName());
        jsonTipperObject.put(InternalConstants.TipperPunkte, tipper.getPoints());
        jsonTipperObject.put(InternalConstants.TipperSpiele, jsonTippArr);
        return jsonTipperObject;
    }

}
