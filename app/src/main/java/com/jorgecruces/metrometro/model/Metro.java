package com.jorgecruces.metrometro.model;

import java.util.ArrayList;

public class Metro {
    private String name;
    private final ArrayList<Line> lines;

    public Metro() {
        lines = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }


    public void addLine(Line line) {
            lines.add(line);
    }
}
