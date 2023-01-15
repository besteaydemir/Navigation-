package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphMap {
    public Map<BasicNode, List<BasicNode>> adj = new HashMap<>();

    public void addNode(BasicNode node) {
        adj.put(node, new LinkedList<BasicNode>()); //??
    }

    public void addEdge(BasicNode initial, BasicNode end) {
        System.out.println(adj.get(initial));
        if (adj.get(initial) == null) {
            this.addNode(initial);
        }

        System.out.println(initial + "here" + adj.get(initial));
        adj.get(initial).add(end); //Check conditions
        System.out.println(initial + "here" + adj.get(initial));
    }

    @Override
    public String toString() {
        String allNodes = "";
        for (BasicNode node : this.adj.keySet()) {
            String nodeList = "\r\n" + "Node: " + node + "\r\n";
            for (BasicNode adjNode : this.adj.get(node)) {
                nodeList = nodeList + "\t" + adjNode + "\r\n";
            }
            allNodes = allNodes + nodeList;
            }
        return allNodes;
    }
}
