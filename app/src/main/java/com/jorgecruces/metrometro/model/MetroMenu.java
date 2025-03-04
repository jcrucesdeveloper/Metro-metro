package com.jorgecruces.metrometro.model;

public class MetroMenu {
    private final String metroName;
    private final String color;

    private boolean levelStar;

    public MetroMenu(String metroName, String color) {
        this.metroName = metroName;
        this.color = color;
    }

    public String getMetroName() {
        return metroName;
    }

    public void setLevelStar(boolean levelStar) {
        this.levelStar = levelStar;
    }

    public String getColor() {
        return color;
    }

    public boolean getLevelStar() {
        return levelStar;
    }
}
