package org.example;

import org.example.graph.BasicNode;

public interface HeuristicFunction {
    public double getCost(BasicNode initial, BasicNode target);
}
