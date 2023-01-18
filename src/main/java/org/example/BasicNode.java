package org.example;

import java.util.ArrayList;
import java.util.List;

public class BasicNode {

    private double lon;
    private double lat;


    public BasicNode(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public BasicNode(List<Double> list) {
        this.lon = list.get(0);
        this.lat = list.get(1);
    }



    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BasicNode) {
            BasicNode otherNode = (BasicNode) obj;  //Casting
            //System.out.println("Equals is called");
            return this.lon == otherNode.getLon() && this.lat == otherNode.getLat();  //Check one &

        }

        else { return false; }

    }

    @Override
    public int hashCode() {
        return Double.valueOf(this.lat).hashCode() + Double.valueOf(this.lon).hashCode();
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }


    public String toString() {
        return "Long:" + this.lon + " Lat:" + this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
