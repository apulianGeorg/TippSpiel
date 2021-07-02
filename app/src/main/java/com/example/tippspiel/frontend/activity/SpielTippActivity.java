package com.example.tippspiel.frontend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Map.FileToTipperListMap;
import com.example.tippspiel.backend.Map.TipperToMatchMap;
import com.example.tippspiel.backend.Spiel.Match;
import com.example.tippspiel.backend.Spiel.MatchFactory;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.basics.JSonOutputBuilder;
import com.example.tippspiel.basics.MyFileReader;
import com.example.tippspiel.basics.MyJsonWriter;
import com.example.tippspiel.frontend.rowadapter.RowAdapterTipp;

import java.util.ArrayList;
import java.util.List;

public class SpielTippActivity extends AppCompatActivity {

    String tipperName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final boolean isTippActivity=
                getIntent().getBooleanExtra("isTippActivity", false);
        prepareView(isTippActivity);

        bindAdapterToListView(MatchFactory.getMatches(), isTippActivity);
    }

    private void prepareView(boolean isTippActivity) {
        tipperName=getIntent().getStringExtra("tipperName");

        TippManager.setTipperName(tipperName);
        setContentView(R.layout.activity_tippanzeige);
        handleSaveButton(isTippActivity);
        if (isTippActivity){
            getTipps();
        }
    }

    private void handleSaveButton(boolean isTippActivity) {
        Button saveButton = findViewById(R.id.saveButton);
        if (!isTippActivity) {
            saveButton.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTipps();
                finish();
            }
        });
    }

    private void saveTipps() {
        //Deltaabgleich
        List<Tipper> deltaTipperList = TippManager.getDeltaTipperList();
        String jsonStr= JSonOutputBuilder.getJsonString(deltaTipperList);
        MyJsonWriter.write(jsonStr);
    }

    private void getTipps() {
        List<Tipper> tipperList=
                TippManager.getTipperList(FileToTipperListMap.mapFileToTipperList(MyFileReader.readFile()));
        ArrayList <Match> matches = TipperToMatchMap.mapFileToTipperList(tipperList, tipperName);
        bindAdapterToListView(matches, true);
    }

    private void bindAdapterToListView(ArrayList<Match> matchList, boolean isTippActivity) {
        ListView listView = findViewById(R.id.listview_activity_main);
        RowAdapterTipp adapter = new RowAdapterTipp(this, matchList);
        adapter.setType(isTippActivity);
        listView.setAdapter(adapter);
    }
}
