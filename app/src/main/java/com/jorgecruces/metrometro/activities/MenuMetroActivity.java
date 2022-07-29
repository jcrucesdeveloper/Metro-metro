package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.model.MetroMenu;

import java.util.ArrayList;

public class MenuMetroActivity extends AppCompatActivity {

    ArrayList<MetroMenu> metroMenu;
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
        MetroMenu item1 = new MetroMenu("Hola"," color");
        MetroMenu item2 = new MetroMenu("Hola 2"," color");
        metroMenu.add(item1);
        metroMenu.add(item2);
    }
}