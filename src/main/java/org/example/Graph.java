package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Graph {
    private List<Node> nodes = new ArrayList<>(); // Because it can infer the type, can just write this instead of  ArrayList<ArrayList<Node>()

    //public double distance(Node source, Node target, BiFunction<Node, Node, Double> distance) {
    public double distance(Node source, Node target, Distance distanceFunction) {
//        if (!source.edges.contains(target)) {
//
//        }

        return distanceFunction.calculateDistance(source, target);
    }

    public void addEdge(BasicNode initial, BasicNode end) {
        //
    }

    // The edge function, interface not an abstract class
    // (bc we don't need to share code between the distance functions)


}
