package com.jorgecruces.metrometro.logic;

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
        ArrayList<Station> alternativesWeighted = this.weightStations(alternativesCleaned,position);
        ArrayList<Station> alternatives = this.pickAlternatives(alternativesWeighted);
        return alternatives;
    }

    /**
     * Clean an ArrayList of stations removing the current position and the next position (correct Alternative)
     * @param stations list of Stations
     * @param position position to remove
     * @return an ArrayList of alternatives cleaned
     */
    public ArrayList<Station> cleanAlternatives(ArrayList<Station> stations, int position) {
        ArrayList<Station> deepCopyStations = new ArrayList<>(stations);
        // We erase the current position
        deepCopyStations.remove(position);
        // and the next position
        deepCopyStations.remove(position);
        return deepCopyStations;
    }

    /**
     * Put weight in an ArrayList of stations by repeating stations using proximity
     * @param stations list of Stations
     * @param position position to use proximity
     * @return an ArrayList with weights
     */
    public ArrayList<Station> weightStations(ArrayList<Station> stations, int position) {
        ArrayList<Station> deepCopyStations = new ArrayList<>(stations);
        int weight = 5;
        for (int i = position; i < stations.size(); i++) {
            // Current station (i)
            Station currentStation = deepCopyStations.get(i);
            // Repeat this weight times
            for (int j = weight; j > 0; j--) {
                deepCopyStations.add(currentStation);
            }
            weight--;
            if (weight < 1) {
                weight = 1;
            }
        }
        return deepCopyStations;
    }

    public ArrayList<Station> pickAlternatives(ArrayList<Station> stations) {
        ArrayList<Station> alternatives = new ArrayList<>();
        ArrayList<Station> deepCopyStations = new ArrayList<>(stations);

        Random random = new Random();
        while (alternatives.size() < 3) {
            int randomIndex = random.nextInt(deepCopyStations.size());
            Station randomStation = deepCopyStations.get(randomIndex);
            alternatives.add(randomStation);
            // Erase stations with the same type
            this.filterStationSameType(deepCopyStations,randomStation);
        }
        return alternatives;
    }

    /**
     *
     * @param stations
     * @param station
     */
    private void filterStationSameType(ArrayList<Station> stations, Station station) {

        for (int i = stations.size() - 1; i > 0; i--) {
            Station currentStation = stations.get(i);
            if (currentStation.getName().equals(station.getName())) {
                stations.remove(i);
            }
        }
    }
}


