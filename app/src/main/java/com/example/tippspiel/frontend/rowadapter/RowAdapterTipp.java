package com.example.tippspiel.frontend.rowadapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.Tipp.TippManager;
import java.util.ArrayList;
import static com.example.tippspiel.R.id;
import static com.example.tippspiel.R.layout;

public class RowAdapterTipp extends ArrayAdapter<Spiel> {

    private final Activity _context;
    private final ArrayList<Spiel> spiele;

    static class ViewHolder
    {
        TextView team1Name;
        TextView team2Name;
        EditText tippSpieler;
        EditText ergebnisTipp;
        ImageView team1Flag;
        ImageView team2Flag;
        TextWatcher textWatcher;
    }

    public RowAdapterTipp(Activity context, ArrayList<Spiel> spiele)
    {
        super(context, layout.list_row_tipps, id.team1Name,spiele);
        this._context = context;
        this.spiele = spiele;
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
        }
        holder = (ViewHolder) convertView.getTag();

        setTextWatcher(position, holder);

        setHolderValues(position, holder);

        return convertView;
    }

    private ViewHolder getViewHolder(View convertView) {
        ViewHolder holder;
        holder = new ViewHolder();
        holder.team1Flag = convertView.findViewById(id.team1Flag);
        holder.team1Name = convertView.findViewById(id.team1Name);
        holder.ergebnisTipp= convertView.findViewById(id.ergebnisTipp);
        holder.tippSpieler= convertView.findViewById(id.tippSpieler);
        holder.team2Name = convertView.findViewById(id.team2Name);
        holder.team2Flag = convertView.findViewById(id.team2Flag);
        return holder;
    }

    private void setTextWatcher(final int position, final ViewHolder holder) {
        // Remove any existing TextWatcher that will be keyed to the wrong ListItem
        if (holder.textWatcher != null)
            holder.ergebnisTipp.removeTextChangedListener(holder.textWatcher);

        // Keep a reference to the TextWatcher so that we can remove it later
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int matchId = spiele.get(position).getMatchid();
                TippManager.neuerTipp(holder.tippSpieler, matchId, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        holder.ergebnisTipp.addTextChangedListener(holder.textWatcher);
    }

    private void setHolderValues(int position, ViewHolder holder) {
        int matchId = spiele.get(position).getMatchid();
        holder.ergebnisTipp.setText(
                TippManager.tipperListGet(
                        holder.tippSpieler,
                        matchId));
        holder.team1Flag.setImageDrawable(spiele.get(position).getTeam1().getTeamIcon());
        holder.team1Name.setText(spiele.get(position).getTeam1().getTeamName());
        holder.team2Name.setText(spiele.get(position).getTeam2().getTeamName());
        holder.team2Flag.setImageDrawable(spiele.get(position).getTeam2().getTeamIcon());
    }
}