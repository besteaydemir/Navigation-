package org.example.alternatives;

import org.example.graph.BasicNode;

public interface HeuristicFunction {
    public double getCost(BasicNode initial, BasicNode target);
}
