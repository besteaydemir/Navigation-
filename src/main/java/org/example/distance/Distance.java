package org.example.distance;

import org.example.graph.BasicNode;

public interface Distance {
    public double calculateDistance(BasicNode source, BasicNode target);
    //Do not write it static
}
