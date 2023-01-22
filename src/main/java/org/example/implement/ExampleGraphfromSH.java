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
import java.util.ArrayList;
import java.util.List;


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


        //Get the list of coordinates (Nodes of the graph)
        ArrayList<ArrayList<Double>> coordinateList = sch.features.get(0).geometry.coordinates;
        double smallest = Double.NEGATIVE_INFINITY;
        double largest = Double.NEGATIVE_INFINITY;
        for (ArrayList<Double> doubleList: coordinateList) {

            gmap2.addNode(new BasicNode(doubleList));
        }


        // Get the edges
        for(int i = 1; i < sch.features.size(); i++){
            ArrayList<ArrayList<Double>> lineStrings = sch.features.get(i).geometry.coordinates;

            for (int m = 0; m < lineStrings.size() - 1; m++) {
                // Get the pair
                gmap2.addEdge(new BasicNode(lineStrings.get(m)), new BasicNode(lineStrings.get(m + 1)), distance);
            }
        }

        System.out.println(gmap2.adj.size());

        ShortestPathAlgorithm algorithm = new ShortestPathAlgorithm(gmap2);
        HeuristicFunction h = new HeuristicFunction() {
            @Override
            public double getCost(BasicNode initial, BasicNode target) {
                Distance d = new EuclidianDistance();
                return d.calculateDistance(initial, target);
            }
        };
        System.out.println(algorithm.algorithm(gmap2, new BasicNode(9.8385465,54.4798868),
                new BasicNode(9.8386019,54.4798218), h));

    }
}
