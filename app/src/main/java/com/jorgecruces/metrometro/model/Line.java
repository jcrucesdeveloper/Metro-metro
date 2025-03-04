package com.jorgecruces.metrometro.model;


import java.util.ArrayList;

public class Line {
    private String name;
    private final String color;
    private final ArrayList<Station> stations;

    public Line(String name,String color) {
        stations = new ArrayList<>();
        this.name = name;
        this.color = color;
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

    public String getColor() {
        return color;
    }
}
