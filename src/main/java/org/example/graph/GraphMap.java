package org.example.graph;

import org.example.distance.Distance;
import org.example.distance.HaversineDistance;
import org.example.graph.BasicNode;

import java.util.*;

public class GraphMap {
    private HashMap<BasicNode, HashMap<BasicNode, Double>> adj = new HashMap<>();

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

        if (!adj.containsKey(end)) {
            this.addNode(end);
        }

        double weight = distance.calculateDistance(initial, end);

        adj.get(initial).put(end, weight);

    }


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

    public Set<BasicNode> getNodeEdgeSet(BasicNode node) {
        return adj.get(node).keySet();
    }
    public List<BasicNode> getNodeEdgeList(BasicNode node) {
        List<BasicNode> nodeList = new ArrayList<>(adj.get(node).keySet());
        return nodeList;
    }

    /**
     * Finds the closest node (Haversine distance) to a given lon, lat pair.
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

            }
        }

        return new BasicNode(lonReturn, latReturn);
    }


    public int getEdgeCount() {
        int count = 0;
        for (BasicNode node: this.adj.keySet()) {
            count += this.adj.get(node).size();
        }
        return count;
    }

    public Set<BasicNode> getNodeSet() {
        return this.adj.keySet();
    }

}
