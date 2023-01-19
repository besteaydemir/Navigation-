package org.example;

import java.util.*;

public class ShortestPathAlgorithm {

    public List<BasicNode> algorithm(GraphMap graph, BasicNode initial, BasicNode terminal, HeuristicFunction h) {

        // Initialize the hashmap that holds Node and fScore value pairs
        HashMap<BasicNode, Double> fScore = new HashMap<>();


        for (BasicNode nodeKey : graph.adj.keySet()) { //TODO oha
            fScore.put(nodeKey,  Double.POSITIVE_INFINITY);
        }
        System.out.println(fScore);

        fScore.put(initial, h.getCost(initial, terminal));
        System.out.println(fScore);


        // Initialize the hashmap that holds Node and gScore value pairs
        HashMap<BasicNode, Double> gScore = new HashMap<>();

        for (BasicNode nodeKey : graph.adj.keySet()) { //TODO oha
            gScore.put(nodeKey,  Double.POSITIVE_INFINITY);
        }

        gScore.put(initial, 0.0);
        System.out.println(gScore);



        // The open (undecided) set of nodes that may need to be expanded again.
        PriorityQueue<BasicNode> openSet = new PriorityQueue<BasicNode>(new Comparator<BasicNode>(){
        public int compare(BasicNode node1, BasicNode node2){
            if(fScore.get(node1)> fScore.get(node2)){
                return -1;
            }
            else{
                return 1;
            }
        }
       });
        openSet.add(initial);


        // The node that preceeds the key node on the current cheapest path from initial to that node
        HashMap<BasicNode, BasicNode> cameFrom = new HashMap<>();


        while (!openSet.isEmpty()) {
            System.out.println("df");
            System.out.println(openSet);
            BasicNode current = openSet.poll(); //TODO: bu min mi max mi ya
            System.out.println(current);
            if (current.equals(terminal)) {
                return reconstructPath(cameFrom, current); //TODO
            }

            openSet.remove(current);
            System.out.println("ab");
            System.out.println(openSet);

            for (BasicNode neighborNode : graph.getNodeEdgeSet(current)) {
                double tentativegScore = gScore.get(current) + graph.getEdgeWeight(current, neighborNode);
                System.out.println("g" + tentativegScore);
                if (tentativegScore < gScore.get(neighborNode)) {
                    cameFrom.put(neighborNode, current);
                    System.out.println("camefrom");
                    System.out.println(cameFrom);
                    // TODO if not absent

                    gScore.put(neighborNode, tentativegScore); //TODO
                    fScore.put(neighborNode, tentativegScore + h.getCost(neighborNode, terminal)); //TODO
                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                    System.out.println("be");
                    System.out.println(openSet);
                }
            }
        }
        return new ArrayList<BasicNode>();

    }

    public List<BasicNode> reconstructPath (HashMap<BasicNode, BasicNode> cameFrom, BasicNode current) {
        List<BasicNode> totalPath = new ArrayList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        return totalPath;
    }






}
