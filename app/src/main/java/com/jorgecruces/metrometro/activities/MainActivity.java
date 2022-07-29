package com.jorgecruces.metrometro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.logic.MetroReaderXML;
import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;
import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MetroReaderXML metroReaderXML;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        metroReaderXML = new MetroReaderXML( this);
        Metro metro = metroReaderXML.createMetro();
        ArrayList<Line> lines = metro.getLines();
        ArrayList<Station> stations = lines.get(1).getStations();
        for(Station station : stations) {
            Log.d("lol", station.getName());
        }

    }

    public void goNextActivity(View view) {
        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }
}
