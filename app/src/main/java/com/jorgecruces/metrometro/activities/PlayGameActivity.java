package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.customViews.StationView;
import com.jorgecruces.metrometro.logic.MetroReaderXML;
import com.jorgecruces.metrometro.logic.PickerStationsAlternative;
import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;
import com.jorgecruces.metrometro.model.Station;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class PlayGameActivity extends AppCompatActivity {

    // Static Level Data
    private String lineName;
    private ArrayList<Station> stations;
    private int stationsSize;

    // Non-Static Level Data
    private int position;
    private String currentStationName;
    private String lastStationName;
    private Station correctAlternative;
    private ArrayList<Station> alternatives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        this.setLineName();
        this.initializeLevelData();
        this.initializeLevelViews();
        this.setCurrentStationQuestion(this.position);
        this.testMethod();
    }

    private void testMethod() {
        RelativeLayout linearLayout = findViewById(R.id.gameLayout);
        TextView textView = new TextView(this);
        textView.setText("HOLa");
        textView.setId(View.generateViewId());
        linearLayout.addView(textView);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        Resources r = this.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                285,
                r.getDisplayMetrics()
        );

        layoutParams.setMarginStart(px);

        StationView stationView = new StationView(this);
        stationView.setLayoutParams(layoutParams);
        linearLayout.addView(stationView);


        StationView stationView2 = new StationView(this);
        stationView2.setId(View.generateViewId());

        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        stationView2.setLayoutParams(layoutParams2);
        linearLayout.addView(stationView2);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        stationView.animate().translationX(-1000f).setDuration(3000);

    }

    /**
     * Get the lineName from the Intent
     */
    private void setLineName() {
        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            Toast.makeText(this, "Hubo un problema", Toast.LENGTH_SHORT).show();
            throw new Error("Hubo un problema al iniciar el level");
        }
        this.lineName = extra.getString("LINEA");
    }

    /**
     * Initialize the level data:
     * - Set stations list
     * - Set position to 0
     * - Set stations size
     */
    private void initializeLevelData() {
        this.setStationList();
        this.position = 0;
        this.stationsSize = this.stations.size();
    }

    /**
     * Initialize static level views:
     * - LevelTitleTextView
     * - StationSizeTextView
     */
    private void initializeLevelViews() {
        TextView levelTitleTextView = findViewById(R.id.textViewTitle);
        TextView stationSizeTextView = findViewById(R.id.textViewMaxPosition);

        levelTitleTextView.setText(this.lineName);
        stationSizeTextView.setText(String.valueOf(this.stationsSize));
    }

    private void setStationList() {
        MetroReaderXML metroReaderXML = new MetroReaderXML(this);
        Metro metro = metroReaderXML.createMetro();
        ArrayList<Line> lines = metro.getLines();
        for (Line tempLine: lines) {
            if (tempLine.getName().equals(lineName)) {
                this.stations = tempLine.getStations();
                return;
            }
        }
        throw new Error("Algo fallo");
    }

    /**
     * This method set the current Station Question:
     * - Set current Station Data and View
     * - Set current Alternatives Data and Views
     * @param position position to set the data
     */
    private void setCurrentStationQuestion(int position) {
        // Number Question
        this.setCurrentNumberStationView(position + 1);

        // Current station
        this.setCurrentStationData(position);
        this.setCurrentStationView();

        // Alternatives
        this.setCurrentAlternativesData(position);
        this.setCurrentAlternativesViews();
    }

    private void setCurrentNumberStationView(int position) {
        TextView textViewCurrentNumberQuestion = findViewById(R.id.textViewCurrentNumberQuestion);
        textViewCurrentNumberQuestion.setText(String.valueOf(position));
    }

    private void setCurrentAlternativesData(int position) {
        // Set correct alternativeData
        this.correctAlternative = this.stations.get(position + 1);
        // Set alternatives data
        PickerStationsAlternative pickerStationsAlternative = new PickerStationsAlternative();

        ArrayList<Station> alternatives = pickerStationsAlternative.getAlternatives(this.stations, position);
        alternatives.add(this.correctAlternative);

        this.alternatives = alternatives;
    }

    private void setCurrentAlternativesViews() {

        ArrayList<TextView> alternativesTextView = new ArrayList<>();

        TextView textViewAlternative1 = findViewById(R.id.textViewAlternative1);
        TextView textViewAlternative2 = findViewById(R.id.textViewAlternative2);
        TextView textViewAlternative3 = findViewById(R.id.textViewAlternative3);
        TextView textViewAlternative4 = findViewById(R.id.textViewAlternative4);

        alternativesTextView.add(textViewAlternative1);
        alternativesTextView.add(textViewAlternative2);
        alternativesTextView.add(textViewAlternative3);
        alternativesTextView.add(textViewAlternative4);


        // Shuffle!!!
        Collections.shuffle(alternativesTextView);

        // Set Text Alternatives
        for (int i = 0; i < alternativesTextView.size(); i++) {
            TextView currentTextView = alternativesTextView.get(i);
            Station currentStation = this.alternatives.get(i);
            currentTextView.setText(currentStation.getName());
        }

        // OnClickListener
        for (TextView textView: alternativesTextView) {
            String lineName = textView.getText().toString();
            textView.setOnClickListener(view -> this.checkAlternative(lineName));
        }

    }


    private void setCurrentStationView() {
//        TextView currentStationView = findViewById(R.id.textViewCurrentStation);
//        currentStationView.setText(this.currentStationName);
    }

    private void setCurrentStationData(int position) {
        Station currentStation = this.stations.get(position);
        this.currentStationName = currentStation.getName();
    }

    private void checkAlternative(String alternativeString) {

        if (alternativeString.equals(this.correctAlternative.getName())) {
            // Correct Alternative
            this.onCorrectAlternative();
        } else {
            // Incorrect Alternative
            this.onIncorrectAlternative();

        }
    }

    private void onCorrectAlternative() {
        this.position = this.position + 1;
        if (this.position < (this.stations.size() - 1)) {
            this.setCurrentStationQuestion(this.position);
        }
    }

    private void onIncorrectAlternative() {
        Toast.makeText(this, "INCORRECTO", Toast.LENGTH_SHORT).show();
    }

    public void goBackToMenu(View view) {
        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }

}