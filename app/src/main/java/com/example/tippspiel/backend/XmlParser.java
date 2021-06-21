package com.example.tippspiel.backend;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.tippspiel.Constants;
import com.example.tippspiel.backend.Spiel.Goalgetter;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.Spiel.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParser extends AsyncTask<Void, Void, ArrayList<Spiel>> {

    private Exception exception;
    private ArrayList<Spiel> spieleListe = new ArrayList<>();

    private ArrayList<Spiel> getSpiele(){
        parseJson();
        return spieleListe;
    }

    private void parseJson(){
        String jsonStr = ReadHtmlPageAsJson();
        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(jsonStr);
            for (int arrIdx = 0; arrIdx < jsonArr.length(); arrIdx ++){
                JSONObject test = jsonArr.getJSONObject(arrIdx);
                parseMatch(jsonArr.getJSONObject(arrIdx));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseMatch(JSONObject matchElement) throws JSONException {
        if (matchElement== null){
            return;
        }
        int matchId = Integer.parseInt(matchElement.getString(Constants.MatchId));
        String spielZeit = matchElement.getString(Constants.MatchDateTime);
        String spielOrt = getSpielort(matchElement);
        boolean matchIsFinished= Boolean.parseBoolean(matchElement.getString(Constants.MatchIsFinished));

        Team team1 = getTeam(matchElement.getJSONObject(Constants.Team1));
        Team team2 = getTeam(matchElement.getJSONObject(Constants.Team2));

        Map<Integer, List<Goalgetter>> teamGoalgetterListen = getGoalgetters(matchElement.getJSONArray(Constants.Goals));
        team1.addGoalgetters(teamGoalgetterListen.get(1));
        team2.addGoalgetters(teamGoalgetterListen.get(2));

        spieleListe.add(new Spiel(matchId, team1, team2,spielOrt, spielZeit, matchIsFinished));
    }

    private String getSpielort(JSONObject matchElement) {
        try {
            return matchElement.getJSONObject(Constants.Location).getString(Constants.LocationCity);
        } catch (JSONException e) {
            return "";
        }
    }

    private Team getTeam(JSONObject jsonTeam) {
        try {
            String teamName = jsonTeam.getString(Constants.TeamName);
            Drawable teamIcon = drawableFromUrl(jsonTeam.getString(Constants.TeamIconUrl));
            return new Team(teamName, teamIcon);
        } catch (Exception e) {
            return new Team("",null);
        }
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

    private Map<Integer, List<Goalgetter>> getGoalgetters(JSONArray goalList) {
        List <Goalgetter>  goalgetterList1 = new ArrayList<>();
        List <Goalgetter>  goalgetterList2 = new ArrayList<>();
        Map<Integer, List<Goalgetter>> goalgetterDictionary = new HashMap<>();

        int toreTeam1=0;
        int toreTeam2=0;
        for (int temp = 0; temp < goalList.length(); temp++) {
            try {
                JSONObject jsonGoalGetter = goalList.getJSONObject(temp);
                String goalGetterName = jsonGoalGetter.getString(Constants.GoalGetterName);
                String matchMinute =jsonGoalGetter.getString(Constants.MatchMinute);
                boolean isOwnGoal = Boolean.parseBoolean(jsonGoalGetter.getString(Constants.IsOwnGoal));
                boolean isPenalty = Boolean.parseBoolean(jsonGoalGetter.getString(Constants.IsPenalty));
                if (Integer.parseInt(jsonGoalGetter.getString(Constants.ScoreTeam1))> toreTeam1){
                    toreTeam1++;
                    goalgetterList1.add(new Goalgetter(goalGetterName,matchMinute,isPenalty, isOwnGoal));
                } else {
                    toreTeam2++;
                    goalgetterList2.add(new Goalgetter(goalGetterName,matchMinute,isPenalty, isOwnGoal));
                }
            } catch (JSONException e) {
                return goalgetterDictionary;
            }
        }
        if (goalgetterList1.size() >0){
            goalgetterDictionary.put(1, goalgetterList1);
        }
        if (goalgetterList2.size() >0) {
            goalgetterDictionary.put(2, goalgetterList2);
        }
        return goalgetterDictionary;
    }

    private String ReadHtmlPageAsJson(){
        //URL url = new URL("https://www.spiegel.de/");
        StringBuilder retStr = new StringBuilder();
        try {
            URL url = new URL(Constants.UrlPrefix + Constants.LigaShortcutEm2020);
            InputStream input = url.openStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while((line= reader.readLine())!=null){
                retStr.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr.toString();
    }

    @Override
    protected ArrayList<Spiel> doInBackground(Void... voids) {
        parseJson();
        return spieleListe;
    }

    @Override
    protected void onPostExecute(ArrayList<Spiel> spieleListe) {
        //TODO: Soll da noch eas rein?
    }
}
