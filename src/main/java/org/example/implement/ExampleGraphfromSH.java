package org.example.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.json_class.Class1;
import org.example.distance.Distance;
import org.example.distance.EuclidianDistance;
import org.example.graph.BasicNode;
import org.example.graph.GraphMap;

import java.io.File;
import java.io.IOException;


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


        // Get the list of coordinates (Nodes of the graph)
        double[][] coordinateList = sch.features.get(0).geometry.coordinates;
        for (double[] doubleList: coordinateList) {
            gmap2.addNode(new BasicNode(doubleList));
        }


        // Get the edges
        for(int i = 1; i < sch.features.size(); i++){
            double[][] lineStrings = sch.features.get(i).geometry.coordinates;

            for (int m = 0; m < lineStrings.length - 1; m++) {
                // Get the pair
                gmap2.addEdge(new BasicNode(lineStrings[m]), new BasicNode(lineStrings[m + 1]), distance);
            }
        }

        System.out.println(gmap2.getEdgeCount());

        double smallest = Double.NEGATIVE_INFINITY;
        double largest = Double.POSITIVE_INFINITY;

        System.out.println(gmap2.getNodeSet().size());

        smallest = Double.POSITIVE_INFINITY;
        largest = Double.NEGATIVE_INFINITY;
        for (BasicNode doubleList: gmap2.adj.keySet()) {
            double lat = doubleList.getLat();

            if(largest < lat) {
                largest = lat;
            }
            if(smallest > lat) {
                smallest = lat;
            }

            //gmap2.addNode(new BasicNode(doubleList));
        }
        System.out.println(smallest + " " + largest);

//        ShortestPathAlgorithm algorithm = new ShortestPathAlgorithm(gmap2);
//        HeuristicFunction h = new HeuristicFunction() {
//            @Override
//            public double getCost(BasicNode initial, BasicNode target) {
//                Distance d = new EuclidianDistance();
//                return d.calculateDistance(initial, target);
//            }
//        };
////        System.out.println(algorithm.algorithm(gmap2, new BasicNode(9.8663685,54.4472826),
////                new BasicNode(9.86994,54.4474017), h));
//
//        //System.out.println(gmap2.nextNode(9.86, 54.44));
//
//        System.out.println(new BasicNode(9,10));
//        System.out.println(algorithm.anyLocationDijkstra(new BasicNode(9.8663680,54.4472826),
//                new BasicNode(9.86994,54.4474017)));


    }
}
