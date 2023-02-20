package org.example.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class AStar {
    GraphMap graph;
    HeuristicFunction h;

    public AStar(GraphMap graph, HeuristicFunction h) {
        this.graph = graph;
        this.h = h;
    }

    public ArrayList<BasicNode> algorithm(BasicNode initial, BasicNode terminal) {

        // Initialize the hashmap that holds Node and fScore value pairs
        // fScore of a node shows how expensive a path from initial to terminal which
        // crosses through that node.
        HashMap<BasicNode, Double> fScore = new HashMap<>();


        // Set each fScore to infinity
        for (BasicNode nodeKey : graph.getNodeSet()) {
            fScore.put(nodeKey,  Double.POSITIVE_INFINITY);
        }

        // Set initial node's fScore to the heuristic functions (non-overestimating) estimate
        fScore.put(initial, h.getCost(initial, terminal));


        // Initialize the hashmap that holds Node and gScore value pairs
        // gScore of a node shows the cost of a path from initial to that node
        HashMap<BasicNode, Double> gScore = new HashMap<>();


        // Set each gScore to infinity
        for (BasicNode nodeKey : graph.getNodeSet()) {
            gScore.put(nodeKey,  Double.POSITIVE_INFINITY);
        }

        // Set the initial node's gScore to 0
        gScore.put(initial, 0.0);


        // The open (undecided) set of nodes that may need to be expanded again.
        // The comparator assigns the highest priority to the lowest value
        PriorityQueue<BasicNode> openSet = new PriorityQueue<BasicNode>(new Comparator<BasicNode>(){
            public int compare(BasicNode node1, BasicNode node2){
                // Comparator prioritizes the node with lower fScore
                if(fScore.get(node1)> fScore.get(node2)){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        });
        openSet.add(initial);


        // The node that preceeds the key node on the current cheapest path from initial to that node
        HashMap<BasicNode, BasicNode> cameFrom = new HashMap<>();


        while (!openSet.isEmpty()) {

            // Get the node from openSet with the highest priority (lowest fScore)
            BasicNode current = openSet.poll();


            // Stop if the node is reached
            if (current.equals(terminal)) {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);


            // For each neighborNode, check for improvements in gScore
            for (BasicNode neighborNode : this.graph.getNodeEdgeSet(current)) {
                double tentativegScore = gScore.get(current) + this.graph.getEdgeWeight(current, neighborNode);

                // Check if this path to neighbor is better than the prev. one
                if (tentativegScore < gScore.get(neighborNode)) {
                    cameFrom.put(neighborNode, current);
                    gScore.put(neighborNode, tentativegScore);
                    fScore.put(neighborNode, tentativegScore + this.h.getCost(neighborNode, terminal));

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }

                }
            }
        }
        // Return empty list if no path is found so far
        return new ArrayList<BasicNode>();

    }

    public ArrayList<BasicNode> reconstructPath (HashMap<BasicNode, BasicNode> cameFrom, BasicNode current) {
        ArrayList<BasicNode> totalPath = new ArrayList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        return totalPath;
    }

    public ArrayList<BasicNode> anyLocation(BasicNode initial, BasicNode terminal) {
        BasicNode closestInit = this.graph.nextNode(initial.getLon(), initial.getLat());
        BasicNode closestTerm = this.graph.nextNode(terminal.getLon(), terminal.getLat());

        return this.algorithm(closestInit, closestTerm);
    }
}
