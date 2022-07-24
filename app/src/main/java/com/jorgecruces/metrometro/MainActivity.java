package com.jorgecruces.metrometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jorgecruces.metrometro.logic.MetroReaderXML;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MetroReaderXML metroReaderXML = new MetroReaderXML("metro_santiago.xml", this);
    }
}