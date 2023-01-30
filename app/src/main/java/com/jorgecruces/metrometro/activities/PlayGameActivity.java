package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
    private String lineColorHex;
    private ArrayList<Station> stations;
    private int stationsSize;

    // Non-Static Level Data
    private int position;
    private String currentStationName;
    private String lastStationName;
    private Station correctAlternative;
    private ArrayList<Station> alternatives;
    private ArrayList<StationView> stationViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        this.setLineName();
        this.initializeLevelData();
        this.initializeLevelViews();
        this.setCurrentStationQuestion(this.position);
        this.drawStationView(150, this.currentStationName, this.lineColorHex);
    }

    private void drawStationView(float marginStart, String stationName, String colorHex) {
        RelativeLayout gameLayout = findViewById(R.id.gameLayout);
        // Layout Params
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        // Margin Start
        layoutParams.setMarginStart(this.transformDpToPixel(marginStart));

        // Add Station View to GameLayout
        StationView stationView = new StationView(this);
        stationView.setLineName(stationName);
        stationView.setColor(colorHex);
        stationView.setLayoutParams(layoutParams);

        stationViews.add(stationView);
        gameLayout.addView(stationView);
    }

    /**
     * Transform Dp value to pixel
     * @param valueDp dp value that is going to be transformed to pixel
     * @return Pixel value
     */
    private int transformDpToPixel(float valueDp) {
        Resources r = this.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                valueDp,
                r.getDisplayMetrics()
        );
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
        this.stationViews = new ArrayList<>();
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
                // TODO - Check this
                this.lineColorHex = tempLine.getColor();
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

        // Alternatives
        this.setCurrentAlternativesData(position);
        this.setCurrentAlternativesViews();

        // StationView
        this.setStationView(position);
    }

    private void scrollView() {
        HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.mainScrollView);
        scrollView.setSmoothScrollingEnabled(true);
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100);
    }

    private void setStationView(int position) {
        if (position == 0) {return;}
        float margin = this.calculateMargin(position);
        this.drawStationView(margin, this.currentStationName, this.lineColorHex);
        this.scrollView();
    }

    private float calculateMargin(int position) {
        float margin = 120;
        float sizeStation = 300;
        float offset = 10 * position;
        return margin + (sizeStation * position) - offset;
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
        } else {
            this.onWinLevel();
        }
    }

    public void onWinLevel() {
        this.updateProgressInfo();
        this.showWinningDialog();
    }

    private void updateProgressInfo() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(this.lineName,true);
        editor.commit();
    }

    private void showWinningDialog() {
        // Show dialog
        Dialog winningDialog = new Dialog(this);
        winningDialog.setContentView(R.layout.winning_dialog);

        // Button
        Button winningButton = (Button) winningDialog.findViewById(R.id.winningButton);

        winningButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuMetroActivity.class);
            startActivity(intent);
        });
        winningDialog.show();
    }

    private void onIncorrectAlternative() {
        Toast.makeText(this, "INCORRECTO", Toast.LENGTH_SHORT).show();
        //TODO - add ads
    }

    public void goBackToMenu(View view) {
        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }

}