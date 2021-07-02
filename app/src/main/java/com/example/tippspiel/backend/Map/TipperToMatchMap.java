package com.example.tippspiel.backend.Map;

import com.example.tippspiel.backend.Spiel.Match;
import com.example.tippspiel.backend.Spiel.MatchFactory;
import com.example.tippspiel.backend.Tipp.MatchTipp;
import com.example.tippspiel.backend.Tipp.Tipper;

import java.util.ArrayList;
import java.util.List;

public class TipperToMatchMap {
    public static ArrayList<Match> mapFileToTipperList(List<Tipper> tipperList, String tipperName){
        ArrayList<Match> matchTipperList = new ArrayList<>();
        ArrayList<Match>  matches = MatchFactory.getMatches();
        for (Tipper tipper: tipperList) {
            if (tipper.getName().equals(tipperName)){
                for (MatchTipp matchTipp: tipper.getMatchTippList()){
                    matchTipperList.add(matchTippToMatch(matchTipp, matches));
                }
            }
        }
        return matchTipperList;
    }

    private static Match matchTippToMatch(MatchTipp matchTipp, ArrayList<Match> matches) {
        Match correspondingMatch =
                MatchHelper.getCorrespondingMatchViaMatchID(matchTipp, matches);
        if (correspondingMatch ==null){
            //Evtl. werden die ehemals getippten Spiele nichtmehr angezeigt
            return null;
        }
        Match match = new Match(correspondingMatch);
        match.getTeam1().setGoalsTeam(matchTipp.getGoalsTeam1());
        match.getTeam2().setGoalsTeam(matchTipp.getGoalsTeam2());
        return match;
    }

}
