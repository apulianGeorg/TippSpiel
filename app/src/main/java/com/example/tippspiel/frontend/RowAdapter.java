package com.example.tippspiel.frontend;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel;

import java.util.ArrayList;

public class RowAdapter extends ArrayAdapter<Spiel> {

    private final Activity _context;
    private final ArrayList<Spiel> spiele;

    public class ViewHolder
    {
        TextView team1Name;
        TextView team2Name;
        TextView team1Goals;
        TextView team2Goals;
        //TextView team1Flag;
        //TextView team2Flag;
    }

    public RowAdapter(Activity context, ArrayList<Spiel> spiele)
    {
        super(context, R.layout.list_row, R.id.team1Name,spiele);
        this._context = context;
        this.spiele = spiele;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_row,parent,false);


            holder = new ViewHolder();
            holder.team1Name = (TextView) convertView.findViewById(R.id.team1Name);
            //holder.team1Flag = (TextView) convertView.findViewById(R.id.team1FlagUri);
            holder.team1Goals= (TextView) convertView.findViewById(R.id.team1Goals);
            holder.team2Name = (TextView) convertView.findViewById(R.id.team2Name);
            //holder.team2Flag = (TextView) convertView.findViewById(R.id.team2FlagUri);
            holder.team2Goals= (TextView) convertView.findViewById(R.id.team2Goals);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.team1Name.setText(spiele.get(position).getTeam1().getTeamName());
       // holder.team1Flag.setText(spiele.get(position).getTeam1().getTeamIcon());
        holder.team1Goals.setText(spiele.get(position).getTeam1().getGoalsTeam());
        holder.team2Name.setText(""+spiele.get(position).getTeam2().getTeamName());
        // holder.team2Flag.setText(spiele.get(position).getTeam2().getTeamIcon());
        holder.team2Goals.setText(spiele.get(position).getTeam2().getGoalsTeam());

        return convertView;
    }
}