package com.jorgecruces.metrometro.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.jorgecruces.metrometro.R;

public class StationView extends LinearLayout{

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
    }


}
