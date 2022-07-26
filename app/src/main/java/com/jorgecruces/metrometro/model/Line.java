package com.jorgecruces.metrometro.model;


import java.util.ArrayList;

public class Line {
    private String name;
    private ArrayList<Station> stations;

    public Line(String name) {
        stations = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        this.stations.add(station);
    }
}
