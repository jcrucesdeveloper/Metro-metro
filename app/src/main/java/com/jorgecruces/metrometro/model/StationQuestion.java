package com.jorgecruces.metrometro.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class StationQuestion {

    private Station correctStation;


    private ArrayList<Station> alternativesStation;

    public StationQuestion() {
        correctStation = new Station(0, "station0");
        alternativesStation = new ArrayList<>();
    }


    public void addAlternativeStation(Station station) {
        alternativesStation.add(station);
    }

    public void setCorrectStation(Station station) {
        correctStation = station;
    }

    public Station getCorrectStation() {
        return correctStation;
    }

    public ArrayList<Station> getAlternativesStation() {
        return alternativesStation;
    }
}
