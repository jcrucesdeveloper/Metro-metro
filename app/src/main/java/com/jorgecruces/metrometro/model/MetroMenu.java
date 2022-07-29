package com.jorgecruces.metrometro.model;

public class MetroMenu {
    private String metroName;
    private String color;

    public MetroMenu(String metroName, String color) {
        this.metroName = metroName;
        this.color = color;
    }

    public String getMetroName() {
        return metroName;
    }

    public String getColor() {
        return color;
    }
}
