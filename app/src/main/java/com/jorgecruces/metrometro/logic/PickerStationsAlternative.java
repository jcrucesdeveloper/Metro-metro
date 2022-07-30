package com.jorgecruces.metrometro.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jorgecruces.metrometro.logger.LoggerSout;
import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;
import java.util.Random;

public class PickerStationsAlternative {


    /**
     * Position Starts from 1 to len(stations)
     *
     * @param stations
     * @param position
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Station> getAlternatives(ArrayList<Station> stations, int position) {

        ArrayList<Station> alternatives = new ArrayList<>();
        ArrayList<Station> alternativesPickerList = new ArrayList<>();

        // Comenzamos desde una posicion + 2  a la alternativa correcta
        int currentPosition = position + 2;
        int counter = 4;

        for (int i = currentPosition; i < stations.size(); i++) {
            for (int j = counter * 2; j >= 0; j--) {
                alternativesPickerList.add(stations.get(i));
            }
            counter--;
        }

        Random random = new Random();
        // Agregamos a la lista de alternativas las posibles alternativas
        int sizeAlternatives = 3;

        while (sizeAlternatives > 0) {
            Station tempStation = alternativesPickerList.get(random.nextInt(alternativesPickerList.size()));
            alternatives.add(tempStation);
            alternativesPickerList.removeIf(station -> station.getName().equals(tempStation.getName()));
            sizeAlternatives--;
        }

        return alternatives;
    }
}


