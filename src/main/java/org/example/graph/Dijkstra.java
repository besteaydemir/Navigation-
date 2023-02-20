package org.example.graph;

public class Dijkstra extends AStar{

    public Dijkstra(GraphMap graph) {
        super(graph, new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                return 0;
            }
        });
    }
}
