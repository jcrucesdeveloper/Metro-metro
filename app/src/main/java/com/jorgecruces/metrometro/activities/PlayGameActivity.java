package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.logic.MetroReaderXML;
import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;

import java.util.ArrayList;

public class PlayGameActivity extends AppCompatActivity {

    private String lineName;
    private Line line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            lineName = extra.getString("LINEA");
            this.initializeLineData(lineName);
        }
    }

    private void initializeLineData(String lineName) {
        MetroReaderXML metroReaderXML = new MetroReaderXML(this);
        Metro metro = metroReaderXML.createMetro();
        ArrayList<Line> lines = metro.getLines();
        for (Line tempLine: lines) {
            if (tempLine.getName().equals(lineName)) {
                this.line = tempLine;
                break;
            }
        }
    }

}