package com.example.tippspiel.frontend;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.SpielFactory;

import java.util.ArrayList;

public class SpielAnzeige extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielanzeige);

        bindAdapterToListView();
    }

    private void bindAdapterToListView() {
        ListView listView = findViewById(R.id.listview_activity_main);

        ArrayList<Spiel> spieleListe = SpielFactory.getSpiele();

        RowSpielAdapter adapter = new RowSpielAdapter(this, spieleListe);
        listView.setAdapter(adapter);
    }

}
