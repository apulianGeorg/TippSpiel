package com.example.tippspiel.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.example.tippspiel.InternalConstants;
import com.example.tippspiel.R;
import com.example.tippspiel.backend.Spiel.MatchFactory;
import com.example.tippspiel.basics.MyJsonWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To avoid beim JSON Parse NetworkOnMainThreadException
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //MatchFactory.getMatches();
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.SpieleAnzeigenButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zeigeSpieleAn();
            }
        });

        button = findViewById(R.id.HighscoreButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zeigeHighscoreAn();
            }
        });

        button = findViewById(R.id.SpieleClearButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListe();
            }
        });

        button = findViewById(R.id.SpieleTippenButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spieleTippen();
            }
        });
    }

    private void spieleTippen() {
        Intent intent = new Intent(this, TipperLigaSelectActivity.class);
        intent.putExtra("isTippActivity", true);
        startActivity(intent);
    }

    private void zeigeHighscoreAn() {
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }

    private void zeigeSpieleAn() {
        MatchFactory.getMatches();
        Intent intent = new Intent(this, SpielTippActivity.class);
        intent.putExtra("isTippActivity", false);
        //Intent intent = new Intent(this, SpielActivity.class);
        startActivity(intent);
    }
    private void clearListe() {
        MyJsonWriter.write(InternalConstants.EmptyStr);
    }
}
