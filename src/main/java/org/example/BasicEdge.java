package org.example;

import org.example.distance.Distance;
import org.example.graph.BasicNode;

public class BasicEdge {
    private BasicNode initial;
    private BasicNode terminal;

    private double cost;

    public BasicEdge(BasicNode initial, BasicNode terminal, Distance distanceFunction) {
        this.initial = initial;
        this.terminal = terminal;
        this.cost = distanceFunction.calculateDistance(initial, terminal);
    }

    public BasicNode getInitial() {
        return initial;
    }

    public void setInitial(BasicNode initial) {
        this.initial = initial;
    }

    public BasicNode getTerminal() {
        return terminal;
    }

    public void setTerminal(BasicNode terminal) {
        this.terminal = terminal;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
