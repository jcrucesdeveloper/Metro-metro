package com.jorgecruces.metrometro.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.jorgecruces.metrometro.logic.MetroReaderXML;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MetroReaderXML metroReaderXML = new MetroReaderXML("metro_santiago.xml", this);
    }
}
