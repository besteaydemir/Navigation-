package org.example.graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.json_class.TypeFeaturesReader;
import org.example.json_class.TypePropertiesSubReader;
import org.example.json_class.TypeCoordinatesSubSubReader;
import org.example.json_class.TypeSubSubSubReader;

import java.util.*;

/**
 * ShortestPathAlgorithm class that contains the base algorithm,
 * and A* and Dijkstra implementations by changing the heuristic
 * function. In order to use the algorithm, one instance of this class
 * should be constructed and the graph of interest should be passed.
 */
public class ShortestPathAlgorithm {
    GraphMap graph;

    public ShortestPathAlgorithm(GraphMap graph) {
        this.graph = graph;
    }

    /**
     * Base algorithm from which A* and Dijkstra will be constructed with Heuristic function selection.
     *
     * @param initial: Initial node.
     * @param terminal: Terminal node.
     * @param h: The heuristic function, should be admissible.
     * @return The list of nodes from initial to terminal node, empty list if there is none.
     */
    public ArrayList<BasicNode> algorithm(BasicNode initial, BasicNode terminal, HeuristicFunction h) {

        // Initialize the hashmap that holds Node and fScore value pairs
        // fScore of a node shows how expensive a path from initial to terminal which
        // crosses through that node.
        HashMap<BasicNode, Double> fScore = new HashMap<>();


        // Set each fScore to infinity
        for (BasicNode nodeKey : this.graph.getNodeSet()) {
            fScore.put(nodeKey,  Double.POSITIVE_INFINITY);
        }

        // Set initial node's fScore to the heuristic functions (non-overestimating) estimate
        fScore.put(initial, h.getCost(initial, terminal));


        // Initialize the hashmap that holds Node and gScore value pairs
        // gScore of a node shows the cost of a path from initial to that node
        HashMap<BasicNode, Double> gScore = new HashMap<>();


        // Set each gScore to infinity
        for (BasicNode nodeKey : this.graph.getNodeSet()) {
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
                    fScore.put(neighborNode, tentativegScore + h.getCost(neighborNode, terminal));

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


    public ArrayList<BasicNode> anyLocationDijkstra (BasicNode initial, BasicNode terminal) {
        BasicNode closestInit = this.graph.nextNode(initial.getLon(), initial.getLat());
        BasicNode closestTerm = this.graph.nextNode(terminal.getLon(), terminal.getLat());

        // Heuristic function is 0 for Dijkstra
        HeuristicFunction h = new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                return 0;
            }
        };
        return this.algorithm(closestInit, closestTerm, h);
    }


    public ArrayList<BasicNode> anyLocationAStar(BasicNode initial, BasicNode terminal, HeuristicFunction h) {
        BasicNode closestInit = this.graph.nextNode(initial.getLon(), initial.getLat());
        BasicNode closestTerm = this.graph.nextNode(terminal.getLon(), terminal.getLat());

        return this.algorithm(closestInit, closestTerm, h);
    }


    public String pathQuerytoJSON(List <BasicNode> query) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();


        ArrayList<ArrayList<Double>> coordinates= new ArrayList<>();
        for (BasicNode node: query) {
            ArrayList<Double> co = new ArrayList<>();
            co.add(node.getLon());
            co.add(node.getLat());
            coordinates.add(co);
        }

        double[][] intArray = coordinates.stream().map(  u  ->  u.stream().mapToDouble(i->i).toArray()  ).toArray(double[][]::new);


        TypeSubSubSubReader c4 = new TypeSubSubSubReader(0);
        TypeCoordinatesSubSubReader c3 = new TypeCoordinatesSubSubReader("LineString", intArray);
        TypePropertiesSubReader c2 = new TypePropertiesSubReader("Feature", c3, c4);
        ArrayList<TypePropertiesSubReader> features = new ArrayList<TypePropertiesSubReader>();
        features.add(c2);
        TypeFeaturesReader c1 = new TypeFeaturesReader("FeatureCollection", features);

        String jsonString = mapper.writeValueAsString(c1);

        return jsonString;
    }







}
