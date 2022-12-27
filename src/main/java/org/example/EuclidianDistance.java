package org.example;
// Implement the interface

public class EuclidianDistance implements Distance {
    @Override
    public double calculateDistance(Node source, Node target) {
        return Math.sqrt(Math.pow(source.getLat() - target.getLat(), 2)
                + Math.pow(source.getLon() - target.getLon(), 2));
    }
}
