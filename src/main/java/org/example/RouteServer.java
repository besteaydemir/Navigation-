package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.alternatives.HeuristicFunction;
import org.example.distance.Distance;
import org.example.distance.HaversineDistance;
import org.example.graph.BasicNode;
import org.example.graph.GraphMap;
import org.example.graph.ShortestPathAlgorithm;
import org.example.json_class.Class1;
import org.example.json_class.CoordinateRequestReader;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * TCP server class that handles the client requests made from the UI,
 * through Dijkstra and AStar resources.
 * Credits: https://www.geeksforgeeks.org/multithreaded-servers-in-java/
 */
class RouteServer {

    public static void main(String[] args) throws IOException
    {
        ServerSocket server = null;

        // Read the json file and instantiate a graph object
        ObjectMapper mapper = new ObjectMapper();
        Class1 sch = mapper.readValue(new File("src/main/java/org/example/schleswig-holstein.json"), Class1.class);

        // The distance function
        Distance distance = new HaversineDistance();

        // Instantiate the graph
        GraphMap gmap = new GraphMap();

        // Get the list of coordinates (Nodes of the graph)
        double[][] coordinateList = sch.features.get(0).geometry.coordinates;
        for (double[] doubleList: coordinateList) {
            gmap.addNode(new BasicNode(doubleList));
        }


        // Get the edges
        for(int i = 1; i < sch.features.size(); i++){
            double[][] lineStrings = sch.features.get(i).geometry.coordinates;

            for (int m = 0; m < lineStrings.length - 1; m++) {
                // Get the pair
                gmap.addEdge(new BasicNode(lineStrings[m]), new BasicNode(lineStrings[m + 1]), distance);
            }
        }

        ShortestPathAlgorithm alg = new ShortestPathAlgorithm(gmap);

        try {

            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected"
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new ClientHandler object that implements Runnable
                ClientHandler clientSock
                        = new ClientHandler(client, gmap, alg);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final GraphMap graph;
        private final ShortestPathAlgorithm alg;


        // Constructor
        public ClientHandler(Socket socket, GraphMap graph, ShortestPathAlgorithm alg)
        {
            this.clientSocket = socket;
            this.graph = graph;
            this.alg = alg;
        }

        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    // Coordinates are received
                    System.out.printf(
                            " Sent from the client: %s\n", line);

                    // Get the coordinates
                    ObjectMapper mapper = new ObjectMapper();
                    CoordinateRequestReader requestReader = mapper.readValue(line, CoordinateRequestReader.class);

                    ArrayList<BasicNode> path = new ArrayList<>();
                    if (Objects.equals(requestReader.requestType, "dijkstra")) {

                        path = alg.anyLocationDijkstra(
                                new BasicNode(requestReader.coordinates[0][0],
                                        requestReader.coordinates[0][1]),
                                new BasicNode(requestReader.coordinates[1][0],
                                        requestReader.coordinates[1][1]));

                    }
                    else if (Objects.equals(requestReader.requestType, "astar")) {

                        HeuristicFunction h = new HeuristicFunction() {
                            @Override
                            public double getCost(BasicNode initial, BasicNode target) {
                                HaversineDistance h = new HaversineDistance();
                                return h.calculateDistance(initial, target);
                            }
                        };


                        path = alg.anyLocationAStar(new BasicNode(requestReader.coordinates[0][0],
                                        requestReader.coordinates[0][1]),
                                new BasicNode(requestReader.coordinates[1][0], requestReader.coordinates[1][1]), h);

                    }


                    //Convert out to geojson
                    line = alg.pathQuerytoJSON(path);
                    out.println(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

