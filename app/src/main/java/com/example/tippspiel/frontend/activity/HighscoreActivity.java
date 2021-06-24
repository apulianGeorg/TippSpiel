package com.example.tippspiel.frontend.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Tipp.TippMap;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.basics.MyReader;
import com.example.tippspiel.frontend.rowadapter.RowAdapterHighscore;

import java.util.List;

public class HighscoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielanzeige);

        bindAdapterToListView();
    }

    private void bindAdapterToListView() {
        ListView listView = findViewById(R.id.listview_activity_main);

        List<Tipper> tipperListe =
                TippManager.getTipperList(TippMap.mapFileToTipperList(MyReader.readFile()));

        RowAdapterHighscore adapter = new RowAdapterHighscore(this, tipperListe);
        listView.setAdapter(adapter);
    }
}
