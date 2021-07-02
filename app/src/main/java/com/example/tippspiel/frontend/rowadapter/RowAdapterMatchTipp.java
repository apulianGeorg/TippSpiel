package com.example.tippspiel.frontend.rowadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tippspiel.backend.Spiel.Match;
import java.util.ArrayList;
import static com.example.tippspiel.R.id;
import static com.example.tippspiel.R.layout;

public abstract class RowAdapterMatchTipp extends ArrayAdapter<Match> {

    private final Activity _context;
    final ArrayList<Match> matches;

    static class ViewHolder
    {
        TextView team1Name;
        TextView team2Name;
        EditText result;
        ImageView team1Flag;
        ImageView team2Flag;
    }

    public RowAdapterMatchTipp(Activity context, ArrayList<Match> matchList)
    {
        super(context, layout.list_row_tipps, id.team1Name,matchList);
        this._context = context;
        this.matches = matchList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(layout.list_row_tipps,parent,false);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setFocusWatcher(position, holder);

        setHolderValues(position, holder);

        return convertView;
    }

    private ViewHolder getViewHolder(View convertView) {
        ViewHolder holder;
        holder = new ViewHolder();
        holder.team1Flag = convertView.findViewById(id.team1Flag);
        holder.team1Name = convertView.findViewById(id.team1Name);
        holder.result = convertView.findViewById(id.ergebnisTipp);
        holder.team2Name = convertView.findViewById(id.team2Name);
        holder.team2Flag = convertView.findViewById(id.team2Flag);

        formatResult(holder);

        return holder;
    }

    abstract void formatResult(ViewHolder holder) ;

    abstract void setFocusWatcher(final int position, final ViewHolder holder);

    private void setHolderValues(int position, ViewHolder holder) {
        int matchId = matches.get(position).getMatchid();
        getResultText(position, holder, matchId);
        holder.team1Flag.setImageDrawable(matches.get(position).getTeam1().getTeamIcon());
        holder.team1Name.setText(matches.get(position).getTeam1().getTeamName());
        holder.team2Name.setText(matches.get(position).getTeam2().getTeamName());
        holder.team2Flag.setImageDrawable(matches.get(position).getTeam2().getTeamIcon());
    }

    abstract void getResultText(int position, ViewHolder holder, int matchId);
}