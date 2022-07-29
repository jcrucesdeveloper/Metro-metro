package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.logic.MetroReaderXML;
import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;
import com.jorgecruces.metrometro.model.MetroMenu;

import java.util.ArrayList;

public class MenuMetroActivity extends AppCompatActivity {

    ArrayList<MetroMenu> metroMenu;
    private Metro metro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        metroMenu = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_metro);

        this.initializeMetroMenuData();

        RecyclerView recyclerView = findViewById(R.id.menuMetroRecyclerView);
        MenuMetroRecyclerViewAdapter adapter = new MenuMetroRecyclerViewAdapter(this,this.metroMenu);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initializeMetroMenuData() {
        MetroReaderXML metroReaderXML = new MetroReaderXML(this);
        metro = metroReaderXML.createMetro();

        ArrayList<Line> lines = metro.getLines();
        for (Line line : lines ) {
            MetroMenu item = new MetroMenu(line.getName(),line.getColor());
            metroMenu.add(item);
        }
    }

    public void goBackMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}