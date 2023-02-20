package org.example.graph;

import org.example.alternatives.HeuristicFunction;

public class Dijkstra extends AStar{
    public Dijkstra(GraphMap graph, HeuristicFunction h) {
        super(graph, h);
    }
}
