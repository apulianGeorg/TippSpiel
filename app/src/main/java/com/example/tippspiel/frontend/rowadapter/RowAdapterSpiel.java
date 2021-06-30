package com.example.tippspiel.frontend.rowadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tippspiel.backend.Spiel.Match;

import java.util.ArrayList;

import static com.example.tippspiel.R.*;

public class RowAdapterSpiel extends ArrayAdapter<Match> {
    private final Activity _context;
    private final ArrayList<Match> matches;

    static class ViewHolder
    {
        TextView team1Name;
        TextView team2Name;
        TextView ergebnis;
        ImageView team1Flag;
        ImageView team2Flag;
    }

    public RowAdapterSpiel(Activity context, ArrayList<Match> matches)
    {
        super(context, layout.list_row_spiele, id.team1Name,matches);
        this._context = context;
        this.matches = matches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(layout.list_row_spiele,parent,false);


            holder = new ViewHolder();
            holder.team1Flag = convertView.findViewById(id.team1Flag);
            holder.team1Name = convertView.findViewById(id.team1Name);
            holder.ergebnis= convertView.findViewById(id.ergebnis);
            holder.team2Name = convertView.findViewById(id.team2Name);
            holder.team2Flag = convertView.findViewById(id.team2Flag);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.team1Flag.setImageDrawable(matches.get(position).getTeam1().getTeamIcon());
        holder.team1Name.setText(matches.get(position).getTeam1().getTeamName());
        holder.ergebnis.setText(matches.get(position).getResult());
        holder.team2Name.setText(matches.get(position).getTeam2().getTeamName());
        holder.team2Flag.setImageDrawable(matches.get(position).getTeam2().getTeamIcon());

        return convertView;
    }
}