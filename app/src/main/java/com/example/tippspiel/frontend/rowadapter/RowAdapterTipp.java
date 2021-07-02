package com.example.tippspiel.frontend.rowadapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.tippspiel.backend.Spiel.Match;
import com.example.tippspiel.backend.Tipp.MatchTipp;
import com.example.tippspiel.backend.Tipp.TippManager;

import java.util.ArrayList;

public class RowAdapterTipp extends RowAdapterMatchTipp{
    public RowAdapterTipp(Activity context, ArrayList<Match> matchList) {
        super(context, matchList);
    }

    @Override
    void setFocusWatcher(final int position, final ViewHolder holder){

        holder.result.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                int matchId = matches.get(position).getMatchid();
                try {
                    TippManager.addNeuerTippToTipperList(matchId, s.toString());
                    holder.result.setBackgroundColor(Color.TRANSPARENT);
                }
                catch(Exception e){
                    holder.result.setText("");
                    holder.result.setBackgroundColor(Color.RED);
                }
            }
        });
    }

    @Override
    void formatResult(ViewHolder holder) {
        //das muss ich pro Spiel entscheiden, ob editierbar
    }

    @Override
    void getResultText(int position, ViewHolder holder, int matchId) {
        MatchTipp tipp =TippManager.getTippViaMatchId(matchId);
        holder.result.setText(tipp.getResult());
        holder.result.setEnabled(!tipp.isEvaluated());
    }
}
