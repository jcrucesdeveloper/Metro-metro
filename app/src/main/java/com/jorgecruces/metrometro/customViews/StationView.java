package com.jorgecruces.metrometro.customViews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jorgecruces.metrometro.R;

public class StationView extends LinearLayout{

    private TextView textViewStationName;
    private ImageView imageViewBackgroundStation, imageViewForegroundStation;

    public StationView(Context context) {
        super(context);
        this.init();
    }

    public StationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public StationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        inflate(getContext(), R.layout.station_layout, this);
        textViewStationName = findViewById(R.id.textViewStationName);
        imageViewBackgroundStation = findViewById(R.id.imageViewBackgroundStation);
        imageViewForegroundStation = findViewById(R.id.imageViewForegroundStation);
    }

    public void setLineName(String lineName) {
        this.textViewStationName.setText(lineName);
    }

    public void setColor(String hexColor) {
        int color = Color.parseColor(hexColor);
        imageViewBackgroundStation.setColorFilter(color);
        imageViewForegroundStation.setColorFilter(color);
    }


}
