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


        // Instantiate the graph
        GraphMap gmap2 = new GraphMap();


        // Distance metric to calculate the edge costs
        Distance distance = new EuclidianDistance();


        // Get the list of coordinates (Nodes of the graph)
        List<List> coordinateList = sch.features.get(0).geometry.coordinates;
        for (List<Double> doubleList: coordinateList) {
            gmap2.addNode(new BasicNode(doubleList));
        }


        // Get the edges
        for(int i = 1; i < sch.features.size(); i++){
            List<List> lineStrings = sch.features.get(i).geometry.coordinates;

            for (int m = 0; m < lineStrings.size() - 1; m++) {
                // Get the pair from the linestring
                gmap2.addEdge(new BasicNode(lineStrings.get(m)), new BasicNode(lineStrings.get(m + 1)), distance);
            }
        }

        System.out.println(gmap2);

    }
}



