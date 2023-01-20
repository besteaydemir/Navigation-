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
        List<List> coordinateList = sch.features.get(0).geometry.coordinates;
        for (List<Double> doubleList: coordinateList) {
            gmap2.addNode(new BasicNode(doubleList));
        }


        // Get the edges
        for(int i = 1; i < sch.features.size(); i++){
            List<List> lineStrings = sch.features.get(i).geometry.coordinates;

            for (int m = 0; m < lineStrings.size() - 1; m++) {
                // Get the pair
                gmap2.addEdge(new BasicNode(lineStrings.get(m)), new BasicNode(lineStrings.get(m + 1)), distance);
            }
        }

        System.out.println(gmap2.adj.size());

        ShortestPathAlgorithm algorithm = new ShortestPathAlgorithm(gmap2);
        System.out.println(algorithm.anyLocationDijkstra(new BasicNode(9.8370639,54.4746254), new BasicNode(9.8371741,54.4743648)));

    }
}
