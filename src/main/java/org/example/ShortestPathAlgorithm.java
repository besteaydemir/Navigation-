package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Entity;
import org.example.graph.BasicNode;
import org.example.graph.GraphMap;

import java.util.*;

public class ShortestPathAlgorithm {
    GraphMap graph; //Is this stupid -_-, graph classına yaz o zaman methodları -_-

    public ShortestPathAlgorithm(GraphMap graph) {
        this.graph = graph;
    }

    public ArrayList<BasicNode> algorithm(GraphMap graph, BasicNode initial, BasicNode terminal, HeuristicFunction h) {

        // Initialize the hashmap that holds Node and fScore value pairs
        HashMap<BasicNode, Double> fScore = new HashMap<>();


        for (BasicNode nodeKey : graph.adj.keySet()) { //TODO oha
            fScore.put(nodeKey,  Double.POSITIVE_INFINITY);
        }
        //System.out.println(fScore);

        fScore.put(initial, h.getCost(initial, terminal));
        //System.out.println(fScore);


        // Initialize the hashmap that holds Node and gScore value pairs
        HashMap<BasicNode, Double> gScore = new HashMap<>();

        for (BasicNode nodeKey : graph.adj.keySet()) { //TODO oha
            gScore.put(nodeKey,  Double.POSITIVE_INFINITY);
        }

        gScore.put(initial, 0.0);
        //System.out.println(gScore);



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
            //System.out.println("df");
            //System.out.println(openSet);
            BasicNode current = openSet.poll(); //TODO: bu min mi max mi ya
            //System.out.println(current);
            if (current.equals(terminal)) {
                return reconstructPath(cameFrom, current); //TODO
            }

            openSet.remove(current);
            //System.out.println("ab");
            //System.out.println(openSet);

            for (BasicNode neighborNode : graph.getNodeEdgeSet(current)) {
                double tentativegScore = gScore.get(current) + graph.getEdgeWeight(current, neighborNode);
                //System.out.println("g" + tentativegScore);
                if (tentativegScore < gScore.get(neighborNode)) {
                    cameFrom.put(neighborNode, current);
                    //System.out.println("camefrom");
                    //System.out.println(cameFrom);
                    // TODO if not absent

                    gScore.put(neighborNode, tentativegScore); //TODO
                    fScore.put(neighborNode, tentativegScore + h.getCost(neighborNode, terminal)); //TODO
                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                    //System.out.println("be");
                    //System.out.println(openSet);
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
        System.out.println("nodes" + closestInit + closestTerm);
        HeuristicFunction h = new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                return 0;
            }
        };
        return this.algorithm(this.graph, closestInit, closestTerm, h);
    }

    public ArrayList<BasicNode> anyLocationAStar(BasicNode initial, BasicNode terminal, HeuristicFunction h) {
        BasicNode closestInit = this.graph.nextNode(initial.getLon(), initial.getLat());
        BasicNode closestTerm = this.graph.nextNode(terminal.getLon(), terminal.getLat());
        return this.algorithm(this.graph, closestInit, closestTerm, h);
    }

    public String pathQuerytoJSON(List <BasicNode> query) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse //TODO unnecesaary

        ArrayList<ArrayList<Double>> coordinates= new ArrayList<>();
        for (BasicNode node: query) {
            ArrayList<Double> co = new ArrayList<>();
            co.add(node.getLat());
            co.add(node.getLon());
            coordinates.add(co);
        }

        Class4 c4 = new Class4();
        c4.maxspeed = 0;
        Class3 c3 = new Class3();
        c3.type = "";
        c3.coordinates = coordinates;
        Class2 c2 = new Class2();
        c2.type = "";
        c2.geometry = c3;
        c2.properties = c4;
        ArrayList<Class2> features = new ArrayList<Class2>();
        features.add(c2);
        Class1 c1 = new Class1();
        c1.type = "";
        c1.features = features;


        String jsonString = mapper.writeValueAsString(c1);
        System.out.println(jsonString);

        return jsonString;
    }





}
