package org.example.graph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.alternatives.HeuristicFunction;
import org.example.distance.Distance;
import org.example.distance.HaversineDistance;
import org.example.graph.BasicNode;
import org.example.graph.GraphMap;
import org.example.json_class.Class1;
import org.example.json_class.Class2;
import org.example.json_class.Class3;
import org.example.json_class.Class4;

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
     * Base algorithm from which A* and Dijkstra will be constructed.
     * @param graph:
     * @param initial: Initial node.
     * @param terminal: Terminal node.
     * @param h: The heuristic function, should be admissible.
     * @return The list of nodes from initial to terminal node, empty list if there is none.
     */
    public ArrayList<BasicNode> algorithm(GraphMap graph, BasicNode initial, BasicNode terminal, HeuristicFunction h) {

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

            // Get the node from openSet with the highest priority (lowest fValue)
            BasicNode current = openSet.poll();


            // Stop if the node is reached
            if (current.equals(terminal)) {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);


            for (BasicNode neighborNode : graph.getNodeEdgeSet(current)) {
                double tentativegScore = gScore.get(current) + graph.getEdgeWeight(current, neighborNode);

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
        HeuristicFunction h = new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                return 0;
            }
        };
        return this.algorithm(this.graph, closestInit, closestTerm, h); //TODO
    }

    public ArrayList<BasicNode> anyLocationAStar(BasicNode initial, BasicNode terminal) { //TODO add heur here
        BasicNode closestInit = this.graph.nextNode(initial.getLon(), initial.getLat());
        BasicNode closestTerm = this.graph.nextNode(terminal.getLon(), terminal.getLat());

        HeuristicFunction h = new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                Distance d = new HaversineDistance();
                return d.calculateDistance(initial, target);
            }
        };

        return this.algorithm(this.graph, closestInit, closestTerm, h);
    }

    public String pathQuerytoJSON(List <BasicNode> query) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse //TODO unnecesaary

        // Ay bunu arraye çevir bu ne
        ArrayList<ArrayList<Double>> coordinates= new ArrayList<>();
        for (BasicNode node: query) {
            ArrayList<Double> co = new ArrayList<>();
            co.add(node.getLon());
            co.add(node.getLat());
            coordinates.add(co);
        }
//        ArrayList<Double> co = new ArrayList<>();
//        co.add(10.0);
//        co.add(54.0);
//        coordinates.add(co);

        double[][] array = new double[coordinates.size()][];
        double[][] intArray = coordinates.stream().map(  u  ->  u.stream().mapToDouble(i->i).toArray()  ).toArray(double[][]::new);


        Class4 c4 = new Class4();
        c4.maxspeed = 0;
        Class3 c3 = new Class3();
        c3.type = "LineString";
        c3.coordinates = intArray;
        Class2 c2 = new Class2();
        c2.type = "Feature";
        c2.geometry = c3;
        c2.properties = c4;
        ArrayList<Class2> features = new ArrayList<Class2>();
        features.add(c2);
        Class1 c1 = new Class1();
        c1.type = "FeatureCollection";
        c1.features = features;


        String jsonString = mapper.writeValueAsString(c1);
        System.out.println(jsonString);

        return jsonString;
    }





}
