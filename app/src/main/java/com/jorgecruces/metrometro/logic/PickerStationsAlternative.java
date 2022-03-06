package com.jorgecruces.metrometro.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;
import java.util.Random;

public class PickerStationsAlternative {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Station> getStationAlternative(ArrayList<Station> listStations, int position) {

        ArrayList<Station> stationsOut = new ArrayList<>();
        ArrayList<Station> tempListStation = new ArrayList<>(listStations);

        int counter = 4;
        for (int i = (position + 1); i < tempListStation.size(); i++) {

            // Add into the temp list a selected number of times
            for (int j = counter * 2; j >= 0; j--) {
                tempListStation.add(listStations.get(position));
            }
            counter--;
        }

        int sizeStation = 3;
        while (sizeStation > 0) {
            Random random = new Random();
            Station tempStation = tempListStation.get(random.nextInt(tempListStation.size()));
            stationsOut.add(tempStation);
            tempListStation.removeIf(station -> station.getNumber() == tempStation.getNumber());
            sizeStation--;
        }

        return stationsOut;

        }
    }
