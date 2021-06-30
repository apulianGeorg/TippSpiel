package com.example.tippspiel.backend.Tipp;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.backend.Map.FileToTipperListMap;
import com.example.tippspiel.backend.Spiel.Match;
import com.example.tippspiel.backend.Spiel.MatchFactory;
import com.example.tippspiel.basics.MyFileReader;
import com.example.tippspiel.basics.MyJsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TippManager {
    private static String tipperName;
    private static List<Tipper> tipperList = new ArrayList<>();

    public static void neuerTipp(int matchId, String ergebnisTipp) {
        for (Tipper tipper : tipperList) {
            if (tipper.getName().equals(tipperName)) {
                for (MatchTipp matchTipp : tipper.getMatchTippList()) {
                    if (matchTipp.getMatchId() == matchId) {
                        try {
                            matchTipp.setResult(ergebnisTipp);
                        }
                        catch (ArrayIndexOutOfBoundsException ignored){

                        }
                       return;
                    }
                }
                tipper.addMatchTipp(matchId, ergebnisTipp);
                return;
            }
        }
        tipperList.add(new Tipper(tipperName, 0, matchId, ergebnisTipp));
    }

    public static String tipperListGet(int matchId) {
        for (Tipper tipper : tipperList) {
            if (tipper.getName().equals(tipperName)) {
                for (MatchTipp matchTipp : tipper.getMatchTippList()) {
                    if (matchTipp.getMatchId() == matchId) {
                        return matchTipp.getResult();
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

    public void setTipperName(String tipperName) {
        TippManager.tipperName = tipperName;
    }

    private static String getJsonString(List<Tipper> tipperListForJson) {
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

    public static String save() {
        //Deltaabgleich
        List<Tipper> deltaTipperList = getDeltaTipperList();
        calculateTipperPoints(deltaTipperList);
        String jsonStr= getJsonString(deltaTipperList);
        MyJsonWriter.write(jsonStr);
        return jsonStr;
    }

    private static void calculateTipperPoints(List<Tipper> deltaTipperList) {
        ArrayList<Match> matchList = MatchFactory.getMatches();
        for (Tipper deltaTipper : deltaTipperList) {
            int tipperPoints= deltaTipper.getPoints();
            for (MatchTipp deltaMatchTipp :deltaTipper.getMatchTippList()) {
                if (!deltaMatchTipp.isEvaluated()){
                    Match correspondingMatch = getCorrespondigMatch(deltaMatchTipp.getMatchId(), matchList);
                    //noinspection ConstantConditions
                    if (correspondingMatch.isMatchIsFinished()) {
                        int punkte = getMatchPoints(deltaMatchTipp, correspondingMatch);
                        deltaMatchTipp.setMatchTippPoints(punkte);
                        tipperPoints += punkte;
                        deltaMatchTipp.setEvaluated(true);
                    }
                }
            }
            deltaTipper.setPoints(tipperPoints);
        }
    }

    private static int getMatchPoints(MatchTipp deltaMatchTipp, Match correspondigMatch) {
        int toreTeam1=correspondigMatch.getTeam1().getGoalsTeam();
        int toreTeam2=correspondigMatch.getTeam2().getGoalsTeam();
        int tippTeam1 = deltaMatchTipp.getGoalsTeam1();
        int tippTeam2 = deltaMatchTipp.getGoalsTeam2();

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

    private static Match getCorrespondigMatch(int matchId, ArrayList<Match> matchList) {
        for (Match match: matchList) {
            if (match.getMatchid() == matchId){
                return match;
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
        return FileToTipperListMap.mapFileToTipperList(MyFileReader.readFile());
    }

    private static List<Tipper> getDeltaList(List<Tipper> tipperListToAdd, List<Tipper> existingTipperList) {
        List<Tipper> outTipperList = new ArrayList<>(existingTipperList);

        for (Tipper tipperToAdd:tipperListToAdd) {
            List<MatchTipp> outTipperListForTipper = getTipperListForTipper(tipperToAdd.getName(), outTipperList);
            if (outTipperListForTipper != null){
                for (MatchTipp matchTippToAdd: tipperToAdd.getMatchTippList()) {
                    if (!matchTippToAdd.getResult().equals(InternalConstants.EmptyStr)){
                        addOrReplace(matchTippToAdd, outTipperListForTipper);
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
        neuerTipper.setPoints(tipperToAdd.getPoints());
        for (MatchTipp tippListeToAdd: tipperToAdd.getMatchTippList()) {
            if (!tippListeToAdd.getResult().equals(InternalConstants.EmptyStr)){
                neuerTipper.addMatchTipp(tippListeToAdd.getMatchId(),tippListeToAdd.getResult());
            }
        }
        return neuerTipper;
    }

    private static List<MatchTipp> getTipperListForTipper(String tipperNameToAdd, List<Tipper> outTipperList) {
        for (Tipper outTipper: outTipperList) {
            if (outTipper.getName().equals(tipperNameToAdd)){
                return outTipper.getMatchTippList();
            }
        }
        //Den Tipper gibt es noch nicht
        return null;
    }

    private static void addOrReplace(MatchTipp matchTippToAdd, List<MatchTipp> outTipperListForTipper) {
        for (MatchTipp outTipper : outTipperListForTipper) {
            //Wurde das Spiel schon mal von dem Tipper getippt?
            if (outTipper.getMatchId() == matchTippToAdd.getMatchId()){
                //Dann entfernen, wenn noch nicht evaluated
                if (!outTipper.isEvaluated()) {
                    outTipperListForTipper.remove(outTipper);
                    outTipperListForTipper.add(matchTippToAdd);
                }
                return;
            }
        }
        outTipperListForTipper.add(matchTippToAdd);
    }
}
