package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.distance.Distance;
import org.example.distance.EuclidianDistance;
import org.example.distance.HaversineDistance;
import org.example.graph.BasicNode;
import org.example.graph.GraphMap;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Server class
class RouteServer {

    public static void main(String[] args) throws IOException
    {
        ServerSocket server = null;

        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        Class1 sch = mapper.readValue(new File("src/main/java/org/example/schleswig-holstein.json"), Class1.class);

        // The distance function
        Distance distance = new HaversineDistance();

        // Instantiate the graph
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

        ShortestPathAlgorithm alg = new ShortestPathAlgorithm(gmap2);



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

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client, gmap2, alg); //Is this stupid

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
                    // writing the received message from
                    // client
                    System.out.printf(
                            " Sent from the client: %s\n",
                            line); //TODO: Coordinates are received

                    // Get the coordinates

                    double[] numbers = Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();

                    //Get the graph object and path finding alg.
                    //Get the output as list of nodes
                    ArrayList<BasicNode> path = alg.anyLocationDijkstra(new BasicNode(numbers[0], numbers[1]),
                            new BasicNode(numbers[2], numbers[3]));

//                    ArrayList<BasicNode> path = alg.anyLocationDijkstra(new BasicNode(9.8663685,54.4472826),
//                            new BasicNode(9.86994,54.4474017));


                    System.out.println("path here" + path);



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

