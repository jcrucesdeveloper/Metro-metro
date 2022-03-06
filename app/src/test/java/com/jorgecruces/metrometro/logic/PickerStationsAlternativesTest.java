package com.jorgecruces.metrometro.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.jorgecruces.metrometro.model.Station;

import java.util.ArrayList;

public class PickerStationsAlternativesTest {

    private PickerStationsAlternative pickerStationsAlternative;

    private ArrayList<Station> stations;

    @Before
    public void setUp() {

        pickerStationsAlternative = new PickerStationsAlternative();
        Station station1 = new Station(1,"lol");
        Station station2 = new Station(2,"lol");
        Station station3 = new Station(3,"lol");
        Station station4 = new Station(4,"lol");
        Station station5 = new Station(5,"lol");

        stations.add(station1);
        stations.add(station2);
        stations.add(station3);
        stations.add(station4);
        stations.add(station5);
    }

    @Test
    public void testPickerStationsAlternatives() {

        ArrayList<Station> stationsAlternativeStationAlternative = pickerStationsAlternative.getStationAlternative(stations,1);
        assertNotEquals(0,stationsAlternativeStationAlternative.size());
    }


}
