package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExampleGraphfromSH {
    //Read from SH and instantiate a graph object

    public static void main(String[] args) throws IOException {
        // Read the JSON file to an object
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        Class1 sch = mapper.readValue(new File("src/main/java/org/example/schleswig-holstein.json"), Class1.class);

        // The distance function
        Distance distance = new EuclidianDistance();

        //Instantiate the graph
        GraphMap gmap2 = new GraphMap();

//        BasicNode node1 = new BasicNode(3,4);
//        BasicNode node2 = new BasicNode(3,4);
//        System.out.println(node1);
//        System.out.println(node2);
//        System.out.println(node1.hashCode());
//        System.out.println(node2.hashCode());
//        node1.setLat(5);
//        System.out.println(node1);
//        System.out.println(node2);
//        System.out.println(node1.hashCode());
//        System.out.println(node2.hashCode());
//        node1.setLat(5);


        BasicNode exNode = new BasicNode(3,5);
        BasicNode exNode2 = new BasicNode(2,5);
        gmap2.addNode(new BasicNode(2,4));
        gmap2.addNode(new BasicNode(1,4));
        gmap2.addEdge(new BasicNode(2,5), new BasicNode(21,5), distance);
        gmap2.addEdge(new BasicNode(21,5), exNode, distance);
        gmap2.addEdge(new BasicNode(2,4), new BasicNode(1,4), distance);
        gmap2.addEdge(new BasicNode(2,5), new BasicNode(21,5), distance);
        gmap2.addEdge(new BasicNode(3,5), new BasicNode(2,4), distance);

        HeuristicFunction h = new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                Distance distance = new EuclidianDistance();
                return distance.calculateDistance(initial, target);
            }
        };

        ShortestPathAlgorithm algorithm = new ShortestPathAlgorithm();
        List<BasicNode> path = algorithm.algorithm(gmap2, new BasicNode(3,5), new BasicNode(2,4), h);
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






//         //Get the list of coordinates (Nodes of the graph)
//        List<List> coordinateList = sch.features.get(0).geometry.coordinates;
//        for (List<Double> doubleList: coordinateList) {
//            gmap2.addNode(new BasicNode(doubleList));
//        }
//
//
//        // Get the edges
//        for(int i = 1; i < sch.features.size(); i++){
//            List<List> lineStrings = sch.features.get(i).geometry.coordinates;
//
//            for (int m = 0; m < lineStrings.size() - 1; m++) {
//                // Get the pair
//                gmap2.addEdge(new BasicNode(lineStrings.get(m)), new BasicNode(lineStrings.get(m + 1)), distance);
//            }
//        }
//
//        System.out.println(gmap2);

    }
}
