package com.example.tippspiel.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel.Spiel;
import com.example.tippspiel.backend.SpielFactory;
import com.example.tippspiel.backend.Tipp.TippManager;
import com.example.tippspiel.backend.Tipp.TippMap;
import com.example.tippspiel.backend.Tipp.Tipper;
import com.example.tippspiel.basics.MyJsonWriter;
import com.example.tippspiel.basics.MyReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpielTippAnzeige extends AppCompatActivity {
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
                finish();
            }
        });

        bindAdapterToListView();
    }

    private void saveTipps() {
        EditText tippSpielerET =  (EditText) findViewById(R.id.tippSpieler);
        String tippSpieler = tippSpielerET.getText().toString();
        TippManager.setTipperName(tippSpieler);
        MyJsonWriter.write(TippManager.tipperList);
        //getTipps();
    }

    private void getTipps() {
        List<Tipper> tipperList=null;
        File root = android.os.Environment.getExternalStorageDirectory();
        String filePathName= root.getAbsolutePath() + "/download" +InternalConstants.TippFile;
        String tippFileStr = MyReader.loadJSONFromAsset(getBaseContext(), filePathName);
        TippManager.setTipperList(TippMap.mapFileToTipperList(tippFileStr));
    }

    private void bindAdapterToListView() {
        ListView listView = (ListView)findViewById(R.id.listview_activity_main);

        ArrayList<Spiel> spieleListe = SpielFactory.getSpiele();

        adapter = new RowTippAdapter(this, spieleListe);
        listView.setAdapter(adapter);
    }
}
