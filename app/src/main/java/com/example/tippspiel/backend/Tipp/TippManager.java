package com.example.tippspiel.backend.Tipp;

import android.widget.EditText;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.Spiel.SpielFactory;
import com.example.tippspiel.basics.MyJsonWriter;
import com.example.tippspiel.basics.MyReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TippManager {
    private static List<Tipper> tipperList = new ArrayList<>();

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
        tipperList.add(new Tipper(tippSpielerName, 0, matchId, ergebnisTipp));
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

    public static List<Tipper> getTipperList(List<Tipper> mapFileToTipperList) {
        tipperList=mapFileToTipperList;
        return tipperList;
    }

    public static void setTipperName(String tippSpieler) {
        for (Tipper tipper : tipperList) {
            if (tipper.getName().equals(InternalConstants.EmptyStr)){
                tipper.setName(tippSpieler);
                return;
            }

        }
    }

    private static String getJsonString(List<Tipper> tipperListForJson) {
        try {
                JSONArray jsonTipperArr = new JSONArray();
                for (Tipper tipper : tipperListForJson) {
                    JSONArray jsonSpielTippArr = new JSONArray();

                    for (SpielTipp spielTipp: tipper.getSpielTippList()) {
                        JSONObject jsonSpielTippObject = getJsonSpielTippObject(spielTipp);
                        jsonSpielTippArr.put(jsonSpielTippObject);
                    }
                    jsonTipperArr.put(getJsonTipperObject(tipper, jsonSpielTippArr));
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
        jsonSpielTippObject.put(InternalConstants.TipperSpielPunkte, spielTipp.getSpielTippPunkte());
        jsonSpielTippObject.put(InternalConstants.TipperSpielIsEvaluated, spielTipp.isEvaluated());
        return jsonSpielTippObject;
    }

    private static JSONObject getJsonTipperObject(Tipper tipper, JSONArray jsonSpielTippArr) throws JSONException {
        JSONObject jsonTipperObject = new JSONObject();
        jsonTipperObject.put(InternalConstants.TipperId, tipper.getTipperId());
        jsonTipperObject.put(InternalConstants.TipperName, tipper.getName());
        jsonTipperObject.put(InternalConstants.TipperPunkte, tipper.getPunkte());
        jsonTipperObject.put(InternalConstants.TipperSpiele, jsonSpielTippArr);
        return jsonTipperObject;
    }

    public static String save() {
        //Deltaabgleich
        List<Tipper> deltaTipperList = getDeltaTipperList();
        calculateTipperPoints(deltaTipperList);
        String jsonStr= getJsonString(deltaTipperList);
        MyJsonWriter.write(jsonStr);
        return jsonStr;
    }

    private static void calculateTipperPoints(List<Tipper> deltaTipperList) {
        ArrayList<Spiel> spielListe = SpielFactory.getSpiele();
        for (Tipper deltaTipper : deltaTipperList) {
            int tipperPoints= deltaTipper.getPunkte();
            for (SpielTipp deltaSpielTipp :deltaTipper.getSpielTippList()) {
                if (!deltaSpielTipp.isEvaluated()){
                    Spiel correspondigSpiel = getCorrespondigSpiel(deltaSpielTipp.getSpielId(), spielListe);
                    if (correspondigSpiel.isMatchIsFinished()) {
                        int punkte = getSpielPoints(deltaSpielTipp, correspondigSpiel);
                        deltaSpielTipp.setSpielTippPunkte(punkte);
                        tipperPoints += punkte;
                        deltaSpielTipp.setEvaluated(true);
                    }
                }
            }
            deltaTipper.setPunkte(tipperPoints);
        }
    }

    private static int getSpielPoints(SpielTipp deltaSpielTipp, Spiel correspondigSpiel) {
        int toreTeam1=correspondigSpiel.getTeam1().getGoalsTeam();
        int toreTeam2=correspondigSpiel.getTeam2().getGoalsTeam();
        int tippTeam1 = deltaSpielTipp.getToreTeam1();
        int tippTeam2 = deltaSpielTipp.getToreTeam2();

        //Korrektes Ergebnis
        if(tippTeam1==toreTeam1 && tippTeam2 == toreTeam2){
            return 4;
        }
        //Korrekte Tendenz
        if(tippTeam1 - tippTeam2 == toreTeam1 -toreTeam2){
            return 3;
        }
        //Korrekter Sieger
        if(tippTeam1 > tippTeam2  && toreTeam1 > toreTeam2 ||
           tippTeam2 > tippTeam1  && toreTeam2 > toreTeam1){
            return 2;
        }
        return 0;
    }

    private static Spiel getCorrespondigSpiel(int spielId, ArrayList<Spiel> spielListe) {
        for (Spiel spiel: spielListe) {
            if (spiel.getMatchid() == spielId){
                return spiel;
            }
        }
        //Das darf eigentlichj nicht sein. Jeder Tipp muss ein Spiel dazu haben
        return null;
    }

    private static List<Tipper> getDeltaTipperList() {
        List<Tipper> existingTipperList = getExistingTipperList();
        return getDeltaList(tipperList, existingTipperList);
    }

    private static List<Tipper> getExistingTipperList() {
        return TippMap.mapFileToTipperList(MyReader.readFile());
    }

    private static List<Tipper> getDeltaList(List<Tipper> tipperListToAdd, List<Tipper> existingTipperList) {
        List<Tipper> outTipperList = new ArrayList<>(existingTipperList);

        //TODO: Da muss eine HashMap her
        for (Tipper tipperToAdd:tipperListToAdd) {
            List<SpielTipp> outTipperListForTipper = getTipperListForTipper(tipperToAdd.getName(), outTipperList);
            if (outTipperListForTipper != null){
                for (SpielTipp spielTippToAdd: tipperToAdd.getSpielTippList()) {
                    if (!spielTippToAdd.getErgebnis().equals(InternalConstants.EmptyStr)){
                        addOrReplace(spielTippToAdd, outTipperListForTipper);
                    }
                }
            } else {
                outTipperList.add(getNeuerTipper(tipperToAdd, outTipperList.size() + 1));
            }
        }
        return outTipperList;
    }

    private static Tipper getNeuerTipper(Tipper tipperToAdd, int tipperId) {
        //Muss die TipperId setzen
        tipperToAdd.setTipperId(tipperId);
        Tipper neuerTipper = new Tipper();
        neuerTipper.setTipperId(tipperToAdd.getTipperId());
        neuerTipper.setName(tipperToAdd.getName());
        neuerTipper.setPunkte(tipperToAdd.getPunkte());
        for (SpielTipp tippListeToAdd: tipperToAdd.getSpielTippList()) {
            if (!tippListeToAdd.getErgebnis().equals(InternalConstants.EmptyStr)){
                neuerTipper.addSpielTipp(tippListeToAdd.getSpielId(),tippListeToAdd.getErgebnis());
            }
        }
        return neuerTipper;
    }

    private static List<SpielTipp> getTipperListForTipper(String tipperNameToAdd, List<Tipper> outTipperList) {
        for (Tipper outTipper: outTipperList) {
            if (outTipper.getName().equals(tipperNameToAdd)){
                return outTipper.getSpielTippList();
            }
        }
        //Den Tipper gibt es noch nicht
        return null;
    }

    private static void addOrReplace(SpielTipp spielTippToAdd, List<SpielTipp> outTipperListForTipper) {
        for (SpielTipp outTipper : outTipperListForTipper) {
            //Wurde das Spiel schon mal von dem Tipper getippt?
            if (outTipper.getSpielId() == spielTippToAdd.getSpielId()){
                //Dann entfernen, wenn noch nicht evaluated
                if (!outTipper.isEvaluated()) {
                    outTipperListForTipper.remove(outTipper);
                    outTipperListForTipper.add(spielTippToAdd);
                }
                return;
            }
        }
        outTipperListForTipper.add(spielTippToAdd);
    }
}
