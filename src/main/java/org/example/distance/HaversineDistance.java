package org.example.distance;

import org.example.graph.BasicNode;


public class HaversineDistance implements Distance{
    final public double radius = 6371e3;

    /**
     *  Function calculating the Haversine distance between two BasicNodes.
     *  Credits: https://www.movable-type.co.uk/scripts/latlong.html
     * @param source: Starting BasicNode containing lat, lon (degrees).
     * @param target: Target BasicNode containing lat, lon (degrees).
     * @return The Haversine distance (meters).
     */
    public double calculateDistance(BasicNode source, BasicNode target) {
        double sourceLatRad = source.getLat() * Math.PI/180;
        double targetLatRad = source.getLat() * Math.PI/180;
        double latDiffRad = targetLatRad - sourceLatRad;
        double lonDiffRad = (target.getLon()-source.getLon()) * Math.PI/180;

        double a = Math.pow(Math.sin(lonDiffRad/2), 2) +
                Math.cos(sourceLatRad ) * Math.cos(targetLatRad) * Math.pow(Math.sin(latDiffRad/2), 2) ;

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return radius * c;
    }

}
