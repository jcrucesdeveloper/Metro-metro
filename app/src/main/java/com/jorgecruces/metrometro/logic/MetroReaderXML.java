package com.jorgecruces.metrometro.logic;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;
import com.jorgecruces.metrometro.model.Station;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MetroReaderXML {

    private final Context context;

    public MetroReaderXML(Context context) {
        this.context = context;
    }

    public Metro createMetro() {

        // Create metro
        Metro metro = new Metro();
        try {
            InputStream in = this.context.getAssets().open("metro_santiago.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(in);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("line");

            // Recorremos todas las lineas
            for (int i = 0; i < nList.getLength(); i++) {

                Node lineNode =  nList.item(i);
                Line tempLine = getLine(lineNode);
                metro.addLine(tempLine);
            }


        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return metro;
    }

    @NonNull
    private static Line getLine(Node lineNode) {
        Element lineElement = (Element) lineNode;

        String lineName = lineElement.getAttribute("name");
        String lineColor = lineElement.getAttribute("color");


        NodeList stations  = lineNode.getChildNodes();
        Line tempLine = new Line(lineName,lineColor);

        for (int j = 0; j < stations.getLength(); j++) {
            Node stationNode = stations.item(j);
            if (stationNode.getNodeType() == Node.ELEMENT_NODE) {
                Node stationNodeFirstChild = stationNode.getFirstChild();
                String nameStation = stationNodeFirstChild.getNodeValue();

                Station tempStation = new Station(nameStation);
                tempLine.addStation(tempStation);
            }
        }
        return tempLine;
    }
}
