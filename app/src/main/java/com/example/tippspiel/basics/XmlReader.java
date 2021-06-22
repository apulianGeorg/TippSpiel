package com.example.tippspiel.basics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.UrlConstants;
import com.example.tippspiel.UrlConstants;
import com.example.tippspiel.backend.Spiel.Goalgetter;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.Spiel.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlReader extends AsyncTask<Void, Void, ArrayList<Spiel>> {

    private ArrayList<Spiel> spieleListe = new ArrayList<>();

    private ArrayList<Spiel> getSpiele(){
        fuelleSpieleListe();
        return spieleListe;
    }

    private void fuelleSpieleListe(){
        String jsonStr = MyReader.
                ReadHtmlPageAsString(UrlConstants.UrlPrefix + UrlConstants.LigaShortcutEm2020);
        try {
            JSONArray jsonArr = new JSONArray(jsonStr);
            for (int arrIdx = 0; arrIdx < jsonArr.length(); arrIdx ++){
                Spiel spiel = getSpiel(jsonArr.getJSONObject(arrIdx));
                spieleListe.add(spiel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Spiel getSpiel(JSONObject matchElement)  {
        if (matchElement== null){
            return null;
        }
        int matchId = getMatchId(matchElement);

        String spielZeit = getSpielZeit(matchElement);
        String spielOrt = getSpielort(matchElement);
        boolean matchIsFinished=isMatchIsFinished(matchElement);

        Team team1 = getTeam(matchElement, UrlConstants.Team1);
        Team team2 = getTeam(matchElement, UrlConstants.Team2);

        Map<Integer, List<Goalgetter>> teamGoalgetterListen = getGoalgetterList(matchElement);
        team1.addGoalgetters(teamGoalgetterListen.get(1));
        team2.addGoalgetters(teamGoalgetterListen.get(2));

        return new Spiel(matchId, team1, team2,spielOrt, spielZeit, matchIsFinished);
    }

    private boolean isMatchIsFinished(JSONObject matchElement) {
        boolean matchIsFinished;
        try {
            matchIsFinished = Boolean.parseBoolean(matchElement.getString(UrlConstants.MatchIsFinished));
        } catch (JSONException e) {
            e.printStackTrace();
            matchIsFinished=false;
        }
        return matchIsFinished;
    }

    private String getSpielZeit(JSONObject matchElement) {
        String spielZeit;
        try {
            spielZeit = matchElement.getString(UrlConstants.MatchDateTime);
        } catch (JSONException e) {
            e.printStackTrace();
            spielZeit= InternalConstants.EmptyStr;
        }
        return spielZeit;
    }

    private int getMatchId(JSONObject matchElement) {
        int matchId;
        try {
            matchId = Integer.parseInt(matchElement.getString(UrlConstants.MatchId));
        }
        catch (JSONException e){
            e.printStackTrace();
            matchId=0;
        }
        return matchId;
    }

    private String getSpielort(JSONObject matchElement) {
        try {
            return matchElement.getJSONObject(UrlConstants.Location).getString(UrlConstants.LocationCity);
        } catch (JSONException e) {
            return InternalConstants.EmptyStr;
        }
    }

    private Team getTeam(JSONObject matchElement, String teamStr) {
        try {
            JSONObject jsonTeam = matchElement.getJSONObject(teamStr);
            String teamName = jsonTeam.getString(UrlConstants.TeamName);
            Drawable teamIcon = drawableFromUrl(jsonTeam.getString(UrlConstants.TeamIconUrl));
            return new Team(teamName, teamIcon);
        } catch (Exception e) {
            e.printStackTrace();
            return new Team(InternalConstants.EmptyStr,null);
        }
    }

    private Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

    private Map<Integer, List<Goalgetter>> getGoalgetterList(JSONObject matchElement) {
        List <Goalgetter>  goalgetterList1 = new ArrayList<>();
        List <Goalgetter>  goalgetterList2 = new ArrayList<>();
        Map<Integer, List<Goalgetter>> goalgetterDictionary = new HashMap<>();

        int toreTeam1=0;
        int toreTeam2=0;
        try {
            JSONArray goalList = matchElement.getJSONArray(UrlConstants.Goals);
            for (int temp = 0; temp < goalList.length(); temp++) {
                JSONObject jsonGoalGetter = goalList.getJSONObject(temp);
                if (Integer.parseInt(jsonGoalGetter.getString(UrlConstants.ScoreTeam1)) > toreTeam1) {
                    toreTeam1++;
                    goalgetterList1.add(getGoalgetter(jsonGoalGetter));
                } else {
                    toreTeam2++;
                    goalgetterList2.add(getGoalgetter(jsonGoalGetter));
                }
            }
        } catch (JSONException e) {
            return goalgetterDictionary;
        }

        if (goalgetterList1.size() >0){
            goalgetterDictionary.put(1, goalgetterList1);
        }
        if (goalgetterList2.size() >0) {
            goalgetterDictionary.put(2, goalgetterList2);
        }
        return goalgetterDictionary;
    }

    private Goalgetter getGoalgetter(JSONObject jsonGoalGetter){
        String goalGetterName = getGoalGetterName(jsonGoalGetter);
        String matchMinute = getMatchMinute(jsonGoalGetter);
        boolean isOwnGoal = isOwnGoal(jsonGoalGetter);
        boolean isPenalty = isPenalty(jsonGoalGetter);
        return new Goalgetter(goalGetterName, matchMinute, isPenalty, isOwnGoal);
    }

    private String getGoalGetterName(JSONObject jsonGoalGetter) {
        String goalGetterName;
        try {
            goalGetterName = jsonGoalGetter.getString(UrlConstants.GoalGetterName);
        } catch (JSONException e) {
            e.printStackTrace();
            goalGetterName=InternalConstants.EmptyStr;
        }
        return goalGetterName;
    }

    private String getMatchMinute(JSONObject jsonGoalGetter) {
        String matchMinute;
        try {
            matchMinute = jsonGoalGetter.getString(UrlConstants.MatchMinute);
        } catch (JSONException e) {
            e.printStackTrace();
            matchMinute=InternalConstants.EmptyStr;
        }
        return matchMinute;
    }

    private boolean isOwnGoal(JSONObject jsonGoalGetter) {
        boolean isOwnGoal;
        try {
            isOwnGoal = Boolean.parseBoolean(jsonGoalGetter.getString(UrlConstants.IsOwnGoal));
        } catch (JSONException e) {
            e.printStackTrace();
            isOwnGoal = false;
        }
        return isOwnGoal;
    }

    private boolean isPenalty(JSONObject jsonGoalGetter) {
        boolean isPenalty;
        try {
            isPenalty = Boolean.parseBoolean(jsonGoalGetter.getString(UrlConstants.IsPenalty));
        } catch (JSONException e) {
            e.printStackTrace();
            isPenalty=false;
        }
        return isPenalty;
    }

    @Override
    protected ArrayList<Spiel> doInBackground(Void... voids) {
        fuelleSpieleListe();
        return spieleListe;
    }

    @Override
    protected void onPostExecute(ArrayList<Spiel> spieleListe) {
        //TODO: Soll da noch eas rein?
    }
}
