package com.jorgecruces.metrometro.model;


import java.util.ArrayList;

public class StationQuestion {

    private Station correctStation;


    private ArrayList<Station> alternativesStation;

    public StationQuestion() {
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
