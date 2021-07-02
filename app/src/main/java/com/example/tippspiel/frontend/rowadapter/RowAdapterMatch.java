package com.example.tippspiel.frontend.rowadapter;

import android.app.Activity;
import android.graphics.Color;
import com.example.tippspiel.backend.Spiel.Match;
import java.util.ArrayList;

public class RowAdapterMatch extends RowAdapterMatchTipp {
    public RowAdapterMatch(Activity context, ArrayList<Match> matchList) {
        super(context, matchList);
    }

    @Override
    void setFocusWatcher(final int position, final ViewHolder holder) {
    }

    @Override
    void formatResult(ViewHolder holder) {
        holder.result.setEnabled(false);
        holder.result.setTextColor(Color.BLACK);
    }

    @Override
    void getResultText(int position, ViewHolder holder, int matchId) {
        holder.result.setText(matches.get(position).getResult());
    }

}
