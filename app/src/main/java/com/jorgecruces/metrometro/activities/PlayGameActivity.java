package com.jorgecruces.metrometro.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class PlayGameActivity extends AppCompatActivity {

    private String lineName;
    private Line line;
    private ArrayList<Station> stations;
    private Station correctStation;
    private ArrayList<Station> alternatives;
    private int position;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Bundle extra = getIntent().getExtras();
        position = 0;
        TextView title = findViewById(R.id.textViewTitle);


        if (extra != null) {
            lineName = extra.getString("LINEA");
            title.setText(lineName);
            this.initializeLineData(lineName);
            this.setStationQuestion(position);
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
        this.stations = this.line.getStations();
    }

    private void setCurrentStation(int position) {
        TextView currentStation = findViewById(R.id.textViewCurrentStation);
        currentStation.setText(this.stations.get(position).getName());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setStationQuestion(int position) {
        this.setCurrentStation(position);
        this.setCorrectStation(position);
        this.setAlternatives(position);
    }

    private void setCorrectStation(int position) {
        this.correctStation = this.stations.get(position + 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAlternatives(int position) {

        PickerStationsAlternative pickerStationsAlternative = new PickerStationsAlternative();
        alternatives = pickerStationsAlternative.getAlternatives(this.line.getStations(), position);

        ArrayList<Station> stationAlternatives = this.getStationAlternatives(position);
        ArrayList<TextView> alternativesTextView = this.getAlternativesTextView();

        for (int i = 0; i < stationAlternatives.size(); i++) {
            Station currentAlternative = stationAlternatives.get(i);
            alternativesTextView.get(i).setText(currentAlternative.getName());
        }
        this.setOnClickListenerAlternatives(alternativesTextView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Station> getStationAlternatives(int position) {

        ArrayList<Station> stations = this.line.getStations();
        Station correctAlternative = stations.get(position + 1);
        Log.d("lol", correctAlternative.getName());


        PickerStationsAlternative pickerStationsAlternative = new PickerStationsAlternative();
        ArrayList<Station> stationAlternatives = pickerStationsAlternative.getAlternatives(stations, position);

        stationAlternatives.add(correctAlternative);
        return stationAlternatives;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setOnClickListenerAlternatives(ArrayList<TextView> alternatives) {
        Station correctAlternative = this.stations.get(this.position + 1);

        for (TextView alternative: alternatives) {
            String lineName = alternative.getText().toString();
            alternative.setOnClickListener(view -> {
                this.checkAlternative(lineName);
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkAlternative(String alternativeString) {

        // Correct alternative
        if (alternativeString.equals(this.correctStation.getName())) {
            this.onCorrectAlternative();
        } else {
            // Incorrect alternative
            Toast.makeText(this, "INCORRECTO", Toast.LENGTH_SHORT).show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onCorrectAlternative() {
        this.position = this.position + 1;
        this.setStationQuestion(this.position);
    }


    public ArrayList<TextView> getAlternativesTextView() {

        ArrayList<TextView> alternativesTextView = new ArrayList<>();

        TextView textViewAlternative1 = findViewById(R.id.textViewAlternative1);
        TextView textViewAlternative2 = findViewById(R.id.textViewAlternative2);
        TextView textViewAlternative3 = findViewById(R.id.textViewAlternative3);
        TextView textViewAlternative4 = findViewById(R.id.textViewAlternative4);

        alternativesTextView.add(textViewAlternative1);
        alternativesTextView.add(textViewAlternative2);
        alternativesTextView.add(textViewAlternative3);
        alternativesTextView.add(textViewAlternative4);

        return alternativesTextView;
    }

    public void goBackToMenu(View view) {
        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }

}