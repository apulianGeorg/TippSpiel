package com.example.tippspiel.backend.Tipp;

import com.example.tippspiel.InternalConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TippMap {
    public static List<Tipper> mapFileToTipperList(String fileInput){
        List<Tipper> tipperList = new ArrayList<>();
        try {
            JSONObject jsonFileObj = new JSONObject(fileInput);
            JSONArray jsonTippArr = new JSONArray(jsonFileObj.getString(InternalConstants.Tipper));
            for (int arrIdx = 0; arrIdx < jsonTippArr.length(); arrIdx ++) {
                tipperList.add(createTipper(jsonTippArr.getJSONObject(arrIdx)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipperList;
    }

    private static Tipper createTipper(JSONObject tipperString) {
        Tipper tipper = new Tipper();
        try {
            tipper.setName(tipperString.getString(InternalConstants.TipperName));
            tipper.setPunkte(Integer.parseInt(tipperString.getString(InternalConstants.TipperPunkte)));
            tipper.setTipperId(Integer.parseInt(tipperString.getString(InternalConstants.TipperId)));
            tipper.setSpielTippList(
                    getTippListe(
                            new JSONArray(tipperString.getString(InternalConstants.TipperSpiele))));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return tipper;
    }

    private static List<SpielTipp> getTippListe(JSONArray jsonArray) {
        ArrayList<SpielTipp> spielTippListe=new ArrayList<>();
        for(int idx =0; idx < jsonArray.length(); idx++){
            SpielTipp spielTipp = new SpielTipp();
            try {
                JSONObject jsonSpielTipp = jsonArray.getJSONObject(idx);
                spielTipp.setErgebnis(jsonSpielTipp.getString(InternalConstants.TipperSpielErgebnis));
                spielTipp.setEvaluated(
                        Boolean.parseBoolean(jsonSpielTipp.getString(InternalConstants.TipperSpielIsEvaluated)));
                spielTipp.setSpielId(
                        Integer.parseInt(jsonSpielTipp.getString(InternalConstants.TipperSpielId)));
                spielTipp.setSpielTippPunkte(
                        Integer.parseInt(jsonSpielTipp.getString(InternalConstants.TipperSpielPunkte)));
                spielTippListe.add(spielTipp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return spielTippListe;
    }

}
