package org.example.json_class;

public class CoordinateRequestReader {

    public double[][] coordinates = new double[2][2];
    public String requestType="";

    public CoordinateRequestReader() {
    }

    public CoordinateRequestReader(double originLon, double originLat, double destinationLon, double destinationLat, String requestType) {
        coordinates[0][0] = originLon;
        coordinates[0][1] = originLat;
        coordinates[1][0] = destinationLon;
        coordinates[1][1] = destinationLat;
        this.requestType = requestType;
    }

}
