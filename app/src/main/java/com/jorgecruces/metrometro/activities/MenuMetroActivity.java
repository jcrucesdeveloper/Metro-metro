package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.logic.MetroReaderXML;
import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;
import com.jorgecruces.metrometro.model.MetroMenu;
import com.jorgecruces.metrometro.sound.BackgroundMusic;
import com.jorgecruces.metrometro.sound.MediaPlayerReproducer;

import java.util.ArrayList;

public class MenuMetroActivity extends AppCompatActivity {

    ArrayList<MetroMenu> metroMenu;
    private Metro metro;
    private TextView scoreStarView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        metroMenu = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_metro);
        scoreStarView = findViewById(R.id.scoreStarView);
    }


    @Override
    protected void onStart() {
        super.onStart();
        BackgroundMusic.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BackgroundMusic.onStop(this);
    }

    @Override
    protected void onResume() {
        this.initializeMetroMenuData();
        RecyclerView recyclerView = findViewById(R.id.menuMetroRecyclerView);
        MenuMetroRecyclerViewAdapter adapter = new MenuMetroRecyclerViewAdapter(this,this.metroMenu);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        super.onResume();
    }

    public void initializeMetroMenuData() {
        MetroReaderXML metroReaderXML = new MetroReaderXML(this);
        metro = metroReaderXML.createMetro();

        ArrayList<Line> lines = metro.getLines();
        for (Line line : lines ) {
            MetroMenu item = new MetroMenu(line.getName(),line.getColor());
            boolean levelStar = this.getLevelStarSharedPreferences("Prueba");
            item.setLevelStar(levelStar);
            metroMenu.add(item);
        }

        // Score Star
        SharedPreferences sharedPref = this.getSharedPreferences(
                String.valueOf(R.string.app_name),Context.MODE_PRIVATE);

        int scoreStar = sharedPref.getInt("score", 0);

        String scoreString = Integer.toString(scoreStar);
        this.scoreStarView.setText(scoreString);
    }

    private boolean getLevelStarSharedPreferences(String lineName) {
        SharedPreferences sharedPref = this.getSharedPreferences(
                String.valueOf(R.string.app_name),Context.MODE_PRIVATE);
        return sharedPref.getBoolean(lineName,false);
    }

    public void goToMainActivity(View view) {
        // Sound Effect
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Sound Effect
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);
        this.goToMainActivity(null);

        super.onBackPressed();
    }
}