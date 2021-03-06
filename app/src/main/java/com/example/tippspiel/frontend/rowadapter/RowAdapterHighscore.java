package com.example.tippspiel.frontend.rowadapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.tippspiel.backend.Tipp.Tipper;

import java.util.Collections;
import java.util.List;

import static com.example.tippspiel.R.*;
import static com.example.tippspiel.R.layout.list_row_tipps;

public class RowAdapterHighscore extends ArrayAdapter<Tipper> {
    private final Activity _context;
    private final List<Tipper> tipperList;

    static class ViewHolder
    {
        TextView tipperRanking;
        TextView tipperName;
        TextView tipperPoints;
    }

    public RowAdapterHighscore(Activity context, List<Tipper> tipperList)
    {
        super(context, list_row_tipps, id.tipperName, tipperList);
        this._context = context;
        Collections.sort(tipperList);
        this.tipperList = tipperList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null)
        {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertView = inflater.inflate(layout.list_row_higscore,parent,false);


            holder = new ViewHolder();
            holder.tipperName = convertView.findViewById(id.tipperName);
            holder.tipperPoints = convertView.findViewById(id.tipperPunkte);
            holder.tipperRanking= convertView.findViewById(id.tipperRanking);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tipperName.setText(tipperList.get(position).getName());
        holder.tipperRanking.setText(Integer.toString(position + 1));
        holder.tipperPoints.setText(Integer.toString(tipperList.get(position).getPoints()));

        return convertView;
    }
}