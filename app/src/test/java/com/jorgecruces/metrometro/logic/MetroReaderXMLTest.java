package com.jorgecruces.metrometro.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MetroReaderXMLTest {

    private MetroReaderXML metroReaderXML;

    @Before
    public void setUp() {
        metroReaderXML = new MetroReaderXML();
    }

    @Test
    public void getMetroNameTest() {
        String name = metroReaderXML.getMetroName("lol");

        assertNotNull(name);
    }


}
