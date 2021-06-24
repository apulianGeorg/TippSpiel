package com.example.tippspiel.frontend.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.Spiel.SpielFactory;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Tipp.TippMap;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.basics.MyReader;
import com.example.tippspiel.frontend.rowadapter.RowAdapterTipp;

import java.util.ArrayList;
import java.util.List;

public class SpielTippActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tippanzeige);

        Button button = findViewById(R.id.getButton);
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

        bindAdapterToListView();
    }

    private void saveTipps() {
        EditText tippSpielerET = findViewById(R.id.tippSpieler);
        String tippSpieler = tippSpielerET.getText().toString();
        TippManager.setTipperName(tippSpieler);
        String outStr = TippManager.save();
        Log.println(Log.INFO,"TippFileStr", outStr);
    }

    private void getTipps() {
        List<Tipper> tipperList=
                TippManager.getTipperList(TippMap.mapFileToTipperList(MyReader.readFile()));
    }

    private void bindAdapterToListView() {
        ListView listView = findViewById(R.id.listview_activity_main);

        ArrayList<Spiel> spieleListe = SpielFactory.getSpiele();

        RowAdapterTipp adapter = new RowAdapterTipp(this, spieleListe);
        listView.setAdapter(adapter);
    }
}
