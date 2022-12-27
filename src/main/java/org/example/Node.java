package org.example;

import java.util.ArrayList;
import java.util.List;

public class Node {


    private double lon;
    private double lat;

    public List<Node> edges = new ArrayList<>();

    public Node(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node otherNode = (Node) obj;  //Casting

            return this.lon == otherNode.lon && this.lat == otherNode.lat;  //Check one &
        }

        else { return false; }

    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
