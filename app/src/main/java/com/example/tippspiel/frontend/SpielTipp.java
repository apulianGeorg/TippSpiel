package com.example.tippspiel.frontend;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.SpielFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class SpielTipp extends AppCompatActivity {
    private Button button;
    private RowTippAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tippanzeige);

        button = (Button) findViewById(R.id.getButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTipps();
            }
        });

        button = (Button) findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTipps();
            }
        });

        bindAdapterToListView();
    }

    private void saveTipps() {
        EditText tippSpielerET =  (EditText) findViewById(R.id.tippSpieler);
        String tippSpieler = tippSpielerET.getText().toString();

        HashMap<Integer, String> spielTipps = this.adapter.getTipps();

    }

    private void getTipps() {
    }

    private void bindAdapterToListView() {
        ListView listView = (ListView)findViewById(R.id.listview_activity_main);

        ArrayList<Spiel> spieleListe = SpielFactory.getSpiele();

        adapter = new RowTippAdapter(this, spieleListe);
        listView.setAdapter(adapter);
    }
}
