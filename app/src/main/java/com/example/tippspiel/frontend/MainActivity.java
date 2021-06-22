package com.example.tippspiel.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tippspiel.R;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.SpieleAnzeigenButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zeigeSpieleAn();
            }
        });

        button = (Button) findViewById(R.id.HighscoreButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zeigeHighscoreAn();
            }
        });

        button = (Button) findViewById(R.id.SpieleTippenButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spieleTippen();
            }
        });
    }

    private void spieleTippen() {
        Intent intent = new Intent(this, SpielTippAnzeige.class);
        startActivity(intent);
    }

    private void zeigeHighscoreAn() {
        Intent intent = new Intent(this, HighscoreAnzeige.class);
        startActivity(intent);
    }

    private void zeigeSpieleAn() {
        Intent intent = new Intent(this, SpielAnzeige.class);
        startActivity(intent);
    }
}
