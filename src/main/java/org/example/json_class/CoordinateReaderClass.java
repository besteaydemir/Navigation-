package org.example.json_class;

public class CoordinateReaderClass {
    public double[][] coordinates = new double[2][2];

    public CoordinateReaderClass(InputPost in) {
        coordinates[0][0] = in.originLon;
        coordinates[0][1] = in.originLat;
        coordinates[1][0] = in.destinationLon;
        coordinates[1][1] = in.destinationLat;
    }
}
