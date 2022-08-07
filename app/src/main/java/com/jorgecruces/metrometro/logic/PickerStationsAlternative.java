package com.jorgecruces.metrometro.logic;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.jorgecruces.metrometro.logger.LoggerSout;
import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;
import java.util.Random;

public class PickerStationsAlternative {


    /**
     * Return List of alternatives using the current position:
     * - It returns 3 alternatives
     * - It does not include the correct alternative
     *
     * @param stations
     * @param position
     * @return
     */
    public ArrayList<Station> getAlternatives(ArrayList<Station> stations, int position) {

        ArrayList<Station> alternativesCleaned = this.cleanAlternatives(stations, position);

        return alternativesCleaned;
    }

    private ArrayList<Station> cleanAlternatives(ArrayList<Station> stations, int position) {


        ArrayList<Station> deepCopyStations = new ArrayList<>(stations);

        // We erase the current position
        deepCopyStations.remove(position);
        // and the next position
        deepCopyStations.remove(position);

        return deepCopyStations;
    }
}


