package org.example.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Class1;
import org.example.HeuristicFunction;
import org.example.ShortestPathAlgorithm;
import org.example.distance.Distance;
import org.example.distance.EuclidianDistance;
import org.example.graph.BasicNode;
import org.example.graph.GraphMap;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SmallGraphTest {

    public static void main(String[] args) throws IOException {
        // The distance function
        Distance distance = new EuclidianDistance();

        //Instantiate the graph
        GraphMap gmap2 = new GraphMap();

        BasicNode exNode = new BasicNode(3,5);
        BasicNode exNode2 = new BasicNode(2,5);
        gmap2.addNode(new BasicNode(2,4));
        gmap2.addNode(new BasicNode(1,4));
        gmap2.addEdge(new BasicNode(2,5), new BasicNode(21,5), distance);
        gmap2.addEdge(new BasicNode(21,5), exNode, distance);
        gmap2.addEdge(new BasicNode(2,4), new BasicNode(1,4), distance);
        gmap2.addEdge(new BasicNode(2,5), new BasicNode(21,5), distance);
        gmap2.addEdge(new BasicNode(3,5), new BasicNode(2,4), distance);
        gmap2.addEdge(new BasicNode(3,5), new BasicNode(21,5), distance);
        gmap2.addEdge(new BasicNode(3,5), new BasicNode(1,4), distance);
        gmap2.addEdge(new BasicNode(1,4), new BasicNode(2,5), distance);
        gmap2.addEdge(new BasicNode(1,4), new BasicNode(3,5), distance);
        gmap2.addEdge(new BasicNode(2,4), new BasicNode(2,5), distance);

        HeuristicFunction h = new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                Distance distance = new EuclidianDistance();
                return distance.calculateDistance(initial, target);
            }
        };

        ShortestPathAlgorithm algorithm = new ShortestPathAlgorithm(gmap2);
        List<BasicNode> path = algorithm.algorithm(gmap2, new BasicNode(21,5), new BasicNode(1,4), h);
        System.out.println(gmap2);
        System.out.println(path);
//        gmap2.addEdge(exNode, new BasicNode(2,5), distance);
//        System.out.println(gmap2.getNodeEdgeList(new BasicNode(2, 5)).get(0));
//        System.out.println(gmap2.getNodeEdgeList(gmap2.getNodeEdgeList(new BasicNode(2, 5)).get(0)));
        //- An node -from an edge- -- no problem

//        gmap2.addNode(new BasicNode(3,5));
//        gmap2.addEdge(new BasicNode(2,5), new BasicNode(21,5));
//        gmap2.addEdge(new BasicNode(2,5), new BasicNode(21,5));
//
//        System.out.println(gmap2);
//
//        System.out.println(gmap2.adj.get(exNode).get(0) == exNode2);
//        System.out.println(gmap2.adj.keySet().toArray()[2] == exNode2);
//        System.out.println(gmap2.adj.get(new BasicNode(2,5)));





    }
}
