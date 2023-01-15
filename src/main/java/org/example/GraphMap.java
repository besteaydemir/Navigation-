package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphMap {
    public Map<BasicNode, List<BasicEdge>> adj = new HashMap<>(); //TODO: Public?

    //TODO: Add the constructor

    public void addNode(BasicNode node) {
        adj.put(node, new LinkedList<BasicEdge>()); //??
    }

    public void addEdge(BasicNode initial, BasicNode terminal, Distance distance) {
        // If the initial node does not exist in the graph
        if (adj.get(initial) == null) {
            this.addNode(initial);
        }

        // Add the edge to the list of edges of that node
        adj.get(initial).add(new BasicEdge(initial, terminal, distance));

    }

    // Overloading eddEdge method
    //public void

    @Override
    public String toString() {
        String allNodes = "";
        for (BasicNode node : this.adj.keySet()) {
            String nodeList = "\r\n" + "Node: " + node + "\r\n";
            for (BasicEdge adjEdge : this.adj.get(node)) {
                nodeList = nodeList + "\t" + adjEdge.getTerminal() + " - Cost: " + adjEdge.getCost() + "\r\n";
            }
            allNodes = allNodes + nodeList;
            }
        return allNodes;
    }
}
