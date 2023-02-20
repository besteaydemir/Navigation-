package org.example.graph;

import org.example.graph.BasicNode;

public interface HeuristicFunction {
    public double getCost(BasicNode initial, BasicNode target);
}
