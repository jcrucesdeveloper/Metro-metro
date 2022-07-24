package com.jorgecruces.metrometro.model;

import java.util.ArrayList;

public class Metro {
    private String name;
    private ArrayList<Station> stations;

    public ArrayList<Station> getStations() {
        return stations;
    }

    public String getName() {
        return name;
    }
}
