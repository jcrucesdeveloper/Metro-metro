package com.jorgecruces.metrometro.model;

import java.util.ArrayList;

public class Metro {
    private String name;
    private ArrayList<Line> lines;


    public String getName() {
        return name;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }
}
