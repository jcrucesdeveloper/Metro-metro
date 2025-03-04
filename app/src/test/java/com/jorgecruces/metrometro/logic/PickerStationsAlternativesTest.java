package com.jorgecruces.metrometro.logic;

import org.junit.Before;
import org.junit.Test;



import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;

public class PickerStationsAlternativesTest {

    private PickerStationsAlternative pickerStationsAlternative;

    private ArrayList<Station> stations;

    @Before
    public void setUp() {

        stations = new ArrayList<>();
        pickerStationsAlternative = new PickerStationsAlternative();
        Station station1 = new Station("station1"); // 0
        Station station2 = new Station("station2");
        Station station3 = new Station("station3");
        Station station4 = new Station("station4");
        Station station5 = new Station("station5");
        Station station6 = new Station("station6");
        Station station7 = new Station("station7");
        Station station8 = new Station("station8");
        Station station9 = new Station("station9");
        Station station10 = new Station("station10");

        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        stations.add(station5);
        stations.add(station6);
        stations.add(station7);
        stations.add(station8);
        stations.add(station9);
        stations.add(station10);
    }

    @Test
    public void testPickerStationsAlternatives() {

        ArrayList<Station> alternatives = pickerStationsAlternative.getAlternatives(stations, 0);
        for(Station station: alternatives) {
            System.out.println(station.getName());
        }

    }

    @Test
    public void testCleanStations() {
        ArrayList<Station> stationsCleaned = pickerStationsAlternative.cleanAlternatives(stations, 2);
        for(Station station: stationsCleaned) {
            System.out.println(station.getName());
        }
    }


    @Test
    public void testWeightStations() {
        ArrayList<Station> stationsCleaned = pickerStationsAlternative.weightStations(stations, 1);
        for(Station station: stationsCleaned) {
            System.out.println(station.getName());
        }
    }

    @Test
    public void testPickAlternatives() {
        ArrayList<Station> stationsCleaned = pickerStationsAlternative.pickAlternatives(stations);
        for(Station station: stationsCleaned) {
            System.out.println(station.getName());
        }
    }

    @Test
    public void testPickerAlternativesComplete() {
        ArrayList<Station> alternatives = pickerStationsAlternative.getAlternatives(stations,0);

        for(Station station: alternatives) {
            System.out.println(station.getName());
        }

    }

}
