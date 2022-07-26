package com.jorgecruces.metrometro.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

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
        Station station1 = new Station("lo1"); // 1
        Station station2 = new Station("lol2");
        Station station3 = new Station("lol3");
        Station station4 = new Station("lol4");
        Station station5 = new Station("lol5");
        Station station6 = new Station("lol6");
        Station station7 = new Station("lol7");
        Station station8 = new Station("lol8");
        Station station9 = new Station("lol9");
        Station station10 = new Station("lol10");

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

        ArrayList<Station> alternatives = pickerStationsAlternative.getAlternatives(stations, 1);
        for(Station station: alternatives) {
            System.out.println(station.getName());
        }

    }


}
