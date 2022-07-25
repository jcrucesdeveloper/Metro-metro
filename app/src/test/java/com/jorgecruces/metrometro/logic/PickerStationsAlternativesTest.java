package com.jorgecruces.metrometro.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.jorgecruces.metrometro.logger.LoggerSout;
import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;
import java.util.logging.Logger;

public class PickerStationsAlternativesTest {

    private PickerStationsAlternative pickerStationsAlternative;

    private ArrayList<Station> stations;

    @Before
    public void setUp() {

        stations = new ArrayList<>();
        pickerStationsAlternative = new PickerStationsAlternative();
        Station station1 = new Station(1,"lo1");
        Station station2 = new Station(2,"lol2");
        Station station3 = new Station(3,"lol3");
        Station station4 = new Station(4,"lol4");
        Station station5 = new Station(5,"lol5");
        Station station6 = new Station(6,"lol6");
        Station station7 = new Station(7,"lol7");
        Station station8 = new Station(8,"lol8");
        Station station9 = new Station(9,"lol9");
        Station station10 = new Station(10,"lol10");

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
<<<<<<< HEAD

        ArrayList<Station> stationsAlternativeStationAlternative = pickerStationsAlternative.getStationAlternatives(stations,0);
        LoggerSout loggerSout = new LoggerSout();

        loggerSout.printArrayList(stationsAlternativeStationAlternative);
=======
        ArrayList<Station> stationsAlternativeStationAlternative = pickerStationsAlternative.getStationAlternative(stations,1);
>>>>>>> e90745559deea2d09d8de6e597418d4e0c1b84f0
        assertNotEquals(0,stationsAlternativeStationAlternative.size());
    }


}
