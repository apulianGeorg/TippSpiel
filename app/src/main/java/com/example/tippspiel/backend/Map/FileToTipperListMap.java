package com.example.tippspiel.backend.Map;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.backend.Tipp.MatchTipp;
import com.example.tippspiel.backend.Tipp.Tipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FileToTipperListMap {
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
            tipper.setPoints(Integer.parseInt(tipperString.getString(InternalConstants.TipperPunkte)));
            tipper.setTipperId(Integer.parseInt(tipperString.getString(InternalConstants.TipperId)));
            tipper.setMatchTippList(
                    getTippListe(
                            new JSONArray(tipperString.getString(InternalConstants.TipperSpiele))));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return tipper;
    }

    private static List<MatchTipp> getTippListe(JSONArray jsonArray) {
        ArrayList<MatchTipp> spielTippListe=new ArrayList<>();
        for(int idx =0; idx < jsonArray.length(); idx++){
            MatchTipp spielTipp = new MatchTipp();
            try {
                JSONObject jsonSpielTipp = jsonArray.getJSONObject(idx);
                spielTipp.setResult(jsonSpielTipp.getString(InternalConstants.TipperSpielErgebnis));
                spielTipp.setEvaluated(
                        Boolean.parseBoolean(jsonSpielTipp.getString(InternalConstants.TipperSpielIsEvaluated)));
                spielTipp.setMatchId(
                        Integer.parseInt(jsonSpielTipp.getString(InternalConstants.TipperSpielId)));
                spielTipp.setMatchTippPoints(
                        Integer.parseInt(jsonSpielTipp.getString(InternalConstants.TipperSpielPunkte)));
                spielTippListe.add(spielTipp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return spielTippListe;
    }

}
