package com.example.tippspiel.frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.SpielFactory;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Tipp.TippMap;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.basics.MyJsonWriter;
import com.example.tippspiel.basics.MyReader;

import java.util.ArrayList;
import java.util.List;

public class SpielTippAnzeige extends AppCompatActivity {

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
        TippManager.save();
    }

    private void getTipps() {
        List<Tipper> tipperList=null;
        TippManager.setTipperList(TippMap.mapFileToTipperList(MyReader.readFile()));
    }

    private void bindAdapterToListView() {
        ListView listView = findViewById(R.id.listview_activity_main);

        ArrayList<Spiel> spieleListe = SpielFactory.getSpiele();

        RowTippAdapter adapter = new RowTippAdapter(this, spieleListe);
        listView.setAdapter(adapter);
    }
}
