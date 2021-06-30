package com.example.tippspiel.frontend.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Map.FileToTipperListMap;
import com.example.tippspiel.backend.Map.TipperToMatchMap;
import com.example.tippspiel.backend.Spiel.Match;
import com.example.tippspiel.backend.Spiel.MatchFactory;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.basics.MyFileReader;
import com.example.tippspiel.frontend.rowadapter.RowAdapterTipp;

import java.util.ArrayList;
import java.util.List;

public class SpielTippActivity extends AppCompatActivity {

    String tipperName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tipperName=getIntent().getStringExtra("tipperName");
        TippManager.setTipperName(tipperName);
        setContentView(R.layout.activity_tippanzeige);
        getTipps();


        Button button = findViewById(R.id.getButton);
        //TODO: Das muss dann raus
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTipps();
            }
        });

        button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTipps();
                finish();
            }
        });

        bindAdapterToListView(MatchFactory.getMatches());
    }

    private void saveTipps() {
        String outStr = TippManager.save();
        Log.println(Log.INFO,"TippFileStr", outStr);
    }

    private void getTipps() {
        List<Tipper> tipperList=
                TippManager.getTipperList(FileToTipperListMap.mapFileToTipperList(MyFileReader.readFile()));
        ArrayList <Match> matches = TipperToMatchMap.mapFileToTipperList(tipperList, tipperName);
        bindAdapterToListView(matches);
    }

    private void bindAdapterToListView(ArrayList<Match> matchList) {
        ListView listView = findViewById(R.id.listview_activity_main);
        RowAdapterTipp adapter = new RowAdapterTipp(this, matchList);
        listView.setAdapter(adapter);
    }
}
