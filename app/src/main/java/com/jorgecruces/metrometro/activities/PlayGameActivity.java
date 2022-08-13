package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.logic.MetroReaderXML;
import com.jorgecruces.metrometro.logic.PickerStationsAlternative;
import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;
import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;
import java.util.Collections;

public class PlayGameActivity extends AppCompatActivity {

    // Static Level Data
    private String lineName;
    private ArrayList<Station> stations;
    private int stationsSize;

    // Non-Static Level Data
    private int position;
    private String currentStationName;
    private String lastStationName;
    private Station correctAlternative;
    private ArrayList<Station> alternatives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        this.setLineName();
        this.initializeLevelData();
        this.initializeLevelViews();

        this.setCurrentStationQuestion(this.position);
    }

    /**
     * Get the lineName from the Intent
     */
    private void setLineName() {
        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            Toast.makeText(this, "Hubo un problema", Toast.LENGTH_SHORT).show();
            throw new Error("Hubo un problema al iniciar el level");
        }
        this.lineName = extra.getString("LINEA");
    }

    /**
     * Initialize the level data:
     * - Set stations list
     * - Set position to 0
     * - Set stations size
     */
    private void initializeLevelData() {
        this.setStationList();
        this.position = 0;
        this.stationsSize = this.stations.size();
    }

    /**
     * Initialize static level views:
     * - LevelTitleTextView
     * - StationSizeTextView
     */
    private void initializeLevelViews() {
        TextView levelTitleTextView = findViewById(R.id.textViewTitle);
        TextView stationSizeTextView = findViewById(R.id.textViewMaxPosition);

        levelTitleTextView.setText(this.lineName);
        stationSizeTextView.setText(String.valueOf(this.stationsSize));
    }

    private void setStationList() {
        MetroReaderXML metroReaderXML = new MetroReaderXML(this);
        Metro metro = metroReaderXML.createMetro();
        ArrayList<Line> lines = metro.getLines();
        for (Line tempLine: lines) {
            if (tempLine.getName().equals(lineName)) {
                this.stations = tempLine.getStations();
                return;
            }
        }
        throw new Error("Algo fallo");
    }

    /**
     * This method set the current Station Question:
     * - Set current Station Data and View
     * - Set current Alternatives Data and Views
     * @param position position to set the data
     */
    private void setCurrentStationQuestion(int position) {
        // Current station
        this.setCurrentStationData(position);
        this.setCurrentStationView();

        // Alternatives
        this.setCurrentAlternativesData(position);
        this.setCurrentAlternativesViews();
    }

    private void setCurrentAlternativesData(int position) {
        // Set correct alternativeData
        this.correctAlternative = this.stations.get(position + 1);
        // Set alternatives data
        PickerStationsAlternative pickerStationsAlternative = new PickerStationsAlternative();

        ArrayList<Station> alternatives = pickerStationsAlternative.getAlternatives(this.stations, position);
        alternatives.add(this.correctAlternative);

        this.alternatives = alternatives;
    }

    private void setCurrentAlternativesViews() {

        ArrayList<TextView> alternativesTextView = new ArrayList<>();

        TextView textViewAlternative1 = findViewById(R.id.textViewAlternative1);
        TextView textViewAlternative2 = findViewById(R.id.textViewAlternative2);
        TextView textViewAlternative3 = findViewById(R.id.textViewAlternative3);
        TextView textViewAlternative4 = findViewById(R.id.textViewAlternative4);

        alternativesTextView.add(textViewAlternative1);
        alternativesTextView.add(textViewAlternative2);
        alternativesTextView.add(textViewAlternative3);
        alternativesTextView.add(textViewAlternative4);
        // Shuffle!!!
        Collections.shuffle(alternativesTextView);

        for (int i = 0; i < alternativesTextView.size(); i++) {
            TextView currentTextView = alternativesTextView.get(i);
            Station currentStation = this.alternatives.get(i);
            currentTextView.setText(currentStation.getName());
        }
    }


    private void setCurrentStationView() {
        TextView currentStationView = findViewById(R.id.textViewCurrentStation);
        currentStationView.setText(this.currentStationName);
    }

    private void setCurrentStationData(int position) {
        Station currentStation = this.stations.get(position);
        this.currentStationName = currentStation.getName();
    }

    private void checkAlternative(String alternativeString) {

        // Correct alternative
        if (alternativeString.equals(this.correctAlternative.getName())) {
            this.onCorrectAlternative();
        } else {
            // Incorrect alternative
            Toast.makeText(this, "INCORRECTO", Toast.LENGTH_SHORT).show();

        }
    }

    private void onCorrectAlternative() {
        this.position = this.position + 1;
//        this.setStationQuestion(this.position);
    }


    public void goBackToMenu(View view) {
        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }

}