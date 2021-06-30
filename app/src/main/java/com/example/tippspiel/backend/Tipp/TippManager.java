package com.example.tippspiel.backend.Tipp;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.backend.Map.FileToTipperListMap;
import com.example.tippspiel.backend.Map.MatchHelper;
import com.example.tippspiel.backend.Map.TipperToMatchMap;
import com.example.tippspiel.backend.Spiel.Match;
import com.example.tippspiel.backend.Spiel.MatchFactory;
import com.example.tippspiel.basics.JSonOutputBuilder;
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

    public static void addNeuerTippToTipperList(int matchId, String ergebnisTipp) {
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

    public static String getResultFRomTipperListViaMatchId(int matchId) {
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

    public static void setTipperName(String tipperName) {
        TippManager.tipperName = tipperName;
    }

    private static void calculateTipperPoints(List<Tipper> deltaTipperList) {
        ArrayList<Match> matchList = MatchFactory.getMatches();
        for (Tipper deltaTipper : deltaTipperList) {
            int tipperPoints= deltaTipper.getPoints();
            for (MatchTipp deltaMatchTipp :deltaTipper.getMatchTippList()) {
                if (!deltaMatchTipp.isEvaluated()){
                    Match correspondingMatch =
                            MatchHelper
                                    .getCorrespondingMatchViaMatchID(deltaMatchTipp, matchList);

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

    public static List<Tipper> getDeltaTipperList() {
        List<Tipper> existingTipperList = getExistingTipperList();
        List<Tipper> deltaTipperList = getDeltaList(tipperList, existingTipperList);
        calculateTipperPoints(deltaTipperList);
        return deltaTipperList;
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
