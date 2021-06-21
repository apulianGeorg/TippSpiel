package com.example.tippspiel.frontend;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tippspiel.backend.Spiel.Spiel;

import java.util.ArrayList;

import static com.example.tippspiel.R.*;

public class RowAdapter extends ArrayAdapter<Spiel> {

    private final Activity _context;
    private final ArrayList<Spiel> spiele;

    public class ViewHolder
    {
        TextView team1Name;
        TextView team2Name;
        TextView ergebnis;
        ImageView team1Flag;
        ImageView team2Flag;
    }

    public RowAdapter(Activity context, ArrayList<Spiel> spiele)
    {
        super(context, layout.list_row_spiele, id.team1Name,spiele);
        this._context = context;
        this.spiele = spiele;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(layout.list_row_spiele,parent,false);


            holder = new ViewHolder();
            holder.team1Flag = (ImageView) convertView.findViewById(id.team1Flag);
            holder.team1Name = (TextView) convertView.findViewById(id.team1Name);
            holder.ergebnis= (TextView) convertView.findViewById(id.ergebnis);
            holder.team2Name = (TextView) convertView.findViewById(id.team2Name);
            holder.team2Flag = (ImageView) convertView.findViewById(id.team2Flag);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.team1Flag.setImageDrawable(spiele.get(position).getTeam1().getTeamIcon());
        holder.team1Name.setText(spiele.get(position).getTeam1().getTeamName());
        holder.ergebnis.setText(spiele.get(position).getResult());
        holder.team2Name.setText(""+spiele.get(position).getTeam2().getTeamName());
        holder.team2Flag.setImageDrawable(spiele.get(position).getTeam2().getTeamIcon());

        return convertView;
    }
}