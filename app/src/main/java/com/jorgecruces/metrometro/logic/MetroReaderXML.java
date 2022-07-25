package com.jorgecruces.metrometro.logic;

import android.content.Context;
import android.util.Log;

import com.jorgecruces.metrometro.model.Metro;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.NodeChangeEvent;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MetroReaderXML {

    private String filename;
    private Context context;

    public MetroReaderXML(String filename, Context context) {

        Log.d("WORKING", "lolazo");
        this.filename = filename;
        this.context = context;
        this.readXML();
    }

    public void readXML() {

        try {

            Log.d("lol", "dentro");
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
                Element lineElement = (Element) lineNode;

                NodeList stations  = lineNode.getChildNodes();

                for (int j = 0; j < stations.getLength(); j++) {
                    Node stationNode = stations.item(j);
                    if (stationNode.getNodeType() == Node.ELEMENT_NODE) {
                        Node firstChild = stationNode.getFirstChild();
                        Log.d("lol", firstChild.getNodeValue());
                    }

                }


//                for (int j = 0; j < stations.getLength(); j++) {
//                    Node stationNode = stations.item(i);
//                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public String getMetroName(String filename) {
        return "name";
    }

    public Metro getMetro() {
        return new Metro();
    }

}
