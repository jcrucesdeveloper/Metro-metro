package com.jorgecruces.metrometro.logic;

import com.jorgecruces.metrometro.model.Metro;


public class MetroReaderXML {
    private String filename;

    public MetroReaderXML(String filename) {
        this.filename = filename;
    }

    public String getMetroName(String filename) {
        return "name";
    }

    public Metro getMetro() {
        return new Metro();
    }

}
