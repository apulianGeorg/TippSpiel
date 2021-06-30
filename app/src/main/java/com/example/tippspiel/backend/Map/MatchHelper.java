package com.example.tippspiel.backend.Map;

import com.example.tippspiel.backend.Spiel.Match;
import com.example.tippspiel.backend.Tipp.MatchTipp;

import java.util.ArrayList;

public class MatchHelper {

    public static Match getCorrespondingMatchViaMatchID(MatchTipp matchTipp, ArrayList<Match> matches) {
        for (Match match:matches) {
            if (match.getMatchid()==matchTipp.getMatchId()){
                return match;
            }
        }
        //Zu jedem Tipp sollte auch ein Match vorhanden sein
        return null;
    }
}
