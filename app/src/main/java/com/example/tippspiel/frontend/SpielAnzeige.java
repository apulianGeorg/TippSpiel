package com.example.tippspiel.frontend;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel;
import com.example.tippspiel.backend.XmlParser;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SpielAnzeige extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielanzeige);

        bindAdapterToListView();
    }

    private void bindAdapterToListView() {
        ListView listView = (ListView)findViewById(R.id.listview_activity_main);

        ArrayList<Spiel> spieleListe = null;
        try {
            spieleListe = new XmlParser().execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RowAdapter adapter = new RowAdapter(this, spieleListe);
        listView.setAdapter(adapter);
    }

}
