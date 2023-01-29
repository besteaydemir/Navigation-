package org.example.graph;

import org.example.distance.Distance;
import org.example.distance.HaversineDistance;
import org.example.graph.BasicNode;

import java.util.*;

public class GraphMap {
    public Map<BasicNode, HashMap<BasicNode, Double>> adj = new HashMap<>(); //TODO: Upcasting, downcasting?
    //TODO: Distance could be here as well

    public void addNode(BasicNode node) {
        // Add if the graph does not have that node
        if (!adj.containsKey(node)) {
            adj.put(node, new HashMap<>()); //??
        }
    }



    public void addEdge(BasicNode initial, BasicNode end, Distance distance) {

        if (!adj.containsKey(initial)) {
            this.addNode(initial);
        }

        if (!adj.containsKey(end)) { //TODO: repetition
            this.addNode(end);
        }

        double weight = distance.calculateDistance(initial, end);

        adj.get(initial).put(end, weight); //TODO: check if exists

    }

//    public HashMap<BasicNode, Double> returnEdges(BasicNode node) {
//        return this.adj.get(node);
//    }

    public double getEdgeWeight(BasicNode node1, BasicNode node2) {
        if (this.adj.get(node1).containsKey(node2)) {
            return this.adj.get(node1).get(node2);
        }

        return 0;
    }

    @Override
    public String toString() {
        String allNodes = "";
        for (BasicNode node : this.adj.keySet()) {
            String nodeList = "\r\n" + "Node: " + node + "\r\n";
            for (BasicNode adjNode : this.adj.get(node).keySet()) {
                nodeList = nodeList + "\t" + adjNode + "\r\n";
            }
            allNodes = allNodes + nodeList;
        }
        return allNodes;
    }

    public Set<BasicNode> getNodeEdgeSet(BasicNode node) { //TODO
        return adj.get(node).keySet();
    }
    public List<BasicNode> getNodeEdgeList(BasicNode node) { //TODO
        List<BasicNode> nodeList = new ArrayList<>(adj.get(node).keySet());
        return nodeList;
    }

    /**
     *
     * @param lon
     * @param lat
     * @return
     */
    public BasicNode nextNode(double lon, double lat) {
        double smallest = Double.POSITIVE_INFINITY;

        double latReturn = lat;
        double lonReturn = lon;
        HaversineDistance d = new HaversineDistance();
        BasicNode a = new BasicNode(lon, lat);

        for (BasicNode nodeKey : this.adj.keySet()) {
            double diff = d.calculateDistance(nodeKey, a);
            if (diff < smallest) {

                smallest = diff;
                latReturn = nodeKey.getLat();
                lonReturn = nodeKey.getLon();
                System.out.println("nodetoreutn" + latReturn + " " + lonReturn + "smallest " + smallest);
            }
        }

        return new BasicNode(lonReturn, latReturn);
    }
}
