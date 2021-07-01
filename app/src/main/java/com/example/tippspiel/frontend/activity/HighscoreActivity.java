package com.example.tippspiel.frontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Map.FileToTipperListMap;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.basics.MyFileReader;
import com.example.tippspiel.frontend.rowadapter.RowAdapterHighscore;

import java.util.List;

public class HighscoreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tippanzeige);

        deactivateSaveButton();

        bindAdapterToListView();
    }

    private void deactivateSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
    }

    private void bindAdapterToListView() {
        ListView listView = findViewById(R.id.listview_activity_main);

        List<Tipper> tipperListe =
                TippManager.getTipperList(FileToTipperListMap.mapFileToTipperList(MyFileReader.readFile()));

        RowAdapterHighscore adapter = new RowAdapterHighscore(this, tipperListe);
        listView.setAdapter(adapter);
    }
}
