package com.example.tippspiel.frontend;

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

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.tippspiel.R.id;
import static com.example.tippspiel.R.layout;

public class RowTippAdapter extends ArrayAdapter<Spiel> {

    private final Activity _context;
    private final ArrayList<Spiel> spiele;
    private final HashMap<Integer, String> tipps;

    public HashMap<Integer, String> getTipps() {
        return tipps;
    }

    public class ViewHolder
    {
        TextView team1Name;
        TextView team2Name;
        EditText ergebnisTipp;
        ImageView team1Flag;
        ImageView team2Flag;
        TextWatcher textWatcher;
    }

    public RowTippAdapter(Activity context, ArrayList<Spiel> spiele)
    {
        super(context, layout.list_row_tipps, id.team1Name,spiele);
        this._context = context;
        this.spiele = spiele;
        this.tipps = new HashMap<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

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
        holder.team1Flag = (ImageView) convertView.findViewById(id.team1Flag);
        holder.team1Name = (TextView) convertView.findViewById(id.team1Name);
        holder.ergebnisTipp= (EditText) convertView.findViewById(id.ergebnisTipp);
        holder.team2Name = (TextView) convertView.findViewById(id.team2Name);
        holder.team2Flag = (ImageView) convertView.findViewById(id.team2Flag);
        return holder;
    }

    private void setTextWatcher(final int position, ViewHolder holder) {
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
                int spielId = spiele.get(position).getMatchid();
                tipps.put(spielId, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        holder.ergebnisTipp.addTextChangedListener(holder.textWatcher);
    }

    private void setHolderValues(int position, ViewHolder holder) {
        int spielId = spiele.get(position).getMatchid();
        if (tipps.containsKey(spielId)){
            holder.ergebnisTipp.setText(tipps.get(spielId));
        } else {
            holder.ergebnisTipp.setText("");
        }
        holder.team1Flag.setImageDrawable(spiele.get(position).getTeam1().getTeamIcon());
        holder.team1Name.setText(spiele.get(position).getTeam1().getTeamName());
        holder.team2Name.setText(""+spiele.get(position).getTeam2().getTeamName());
        holder.team2Flag.setImageDrawable(spiele.get(position).getTeam2().getTeamIcon());
    }
}