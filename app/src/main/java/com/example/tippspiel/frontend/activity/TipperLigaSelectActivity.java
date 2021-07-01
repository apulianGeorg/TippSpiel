package com.example.tippspiel.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tippspiel.R;

public class TipperLigaSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipperselect);
        final boolean isTippActivity=getIntent().getBooleanExtra("isTippActivity", false);

        Button button = findViewById(R.id.tipperButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spieleTippen(isTippActivity);
            }
        });
    }

    private void spieleTippen(boolean isTippActivity) {

        String tipperName=  ((EditText) findViewById(R.id.tipperAuswahlText)).getText().toString().trim();
        if (!tipperName.isEmpty()) {
            Intent intent = new Intent(this, SpielTippActivity.class);
            intent.putExtra("tipperName", tipperName);
            intent.putExtra("isTippActivity", isTippActivity);
            startActivity(intent);
        }
    }
}
