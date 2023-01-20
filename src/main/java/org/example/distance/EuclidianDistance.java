package org.example.distance;
// Implement the interface

import org.example.graph.BasicNode;

public class EuclidianDistance implements Distance {
    @Override
    public double calculateDistance(BasicNode source, BasicNode target) {
        return Math.sqrt(Math.pow(source.getLat() - target.getLat(), 2)
                + Math.pow(source.getLon() - target.getLon(), 2));
    }
}
