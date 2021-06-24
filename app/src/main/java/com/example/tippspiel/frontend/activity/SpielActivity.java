package com.example.tippspiel.frontend.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.Spiel.SpielFactory;
import com.example.tippspiel.frontend.rowadapter.RowAdapterSpiel;

import java.util.ArrayList;

public class SpielActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielanzeige);

        bindAdapterToListView();
    }

    private void bindAdapterToListView() {
        ListView listView = findViewById(R.id.listview_activity_main);

        ArrayList<Spiel> spieleListe = SpielFactory.getSpiele();

        RowAdapterSpiel adapter = new RowAdapterSpiel(this, spieleListe);
        listView.setAdapter(adapter);
    }

}