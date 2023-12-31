package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
//import jdk.internal.util.xml.impl.Input;
import org.example.json_class.CoordinateReaderClass;
import org.example.json_class.CoordinateRequestReader;
import org.example.json_class.InputPost;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.io.*;
import java.net.Socket;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("sysdev")


public class PathReturnResource {
    private static final String OPENROUTESERVICE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private static final String OPENROUTESERVICE_URL2 = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

    private static final String OPENROUTESERVICE_KEY = "5b3ce3597851110001cf62489c868306f2434696a351dca46f78f8f5";



    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return JsonObject response.
     */

    @GET
    @Path("/orsdirections")
    @Produces(MediaType.APPLICATION_JSON)


    public static JsonObject getIt(@QueryParam("originLat") String originLat,
                                   @QueryParam("originLon") String originLon,
                                   @QueryParam("destinationLat") String destinationLat,
                                   @QueryParam("destinationLon") String destinationLon) {
        // use the jersey client api to make HTTP requests

        String startVal = originLon + "," + originLat;
        String endVal = destinationLon + "," + destinationLat;


        final JerseyClient client = new JerseyClientBuilder().build();
        final JerseyWebTarget webTarget = client.target(OPENROUTESERVICE_URL);
        final Response response = webTarget
                .queryParam("start", startVal)
                .queryParam("end", endVal)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", OPENROUTESERVICE_KEY) // send the API key for authentication
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();


        // check the result
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        } else {

            // get the JSON response
            final String responseString = response.readEntity(String.class);
            final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();



            return jsonObject;
        }
    }


    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as "text/plain" media type.
     * @param in Object containing the origin and destination coordinates.
     * @return JsonObject response.
     * @throws IOException
     */
    @POST
    @Path("/orsdirections")
    @Produces(MediaType.APPLICATION_JSON)

    public static JsonObject postIt(InputPost in) throws IOException {
        // use the jersey client api to make HTTP requests

        // Read coordinates as Json String
        CoordinateReaderClass coord = new CoordinateReaderClass(in);

        ObjectMapper mapper = new ObjectMapper();
        String requestString = mapper.writeValueAsString(coord);

        // Convert to JsonObject
        final JsonObject jsonRequestObject = Json.createReader(new StringReader(requestString)).readObject();



        final JerseyClient client1 = new JerseyClientBuilder().build();
        final JerseyWebTarget webTarget = client1.target(OPENROUTESERVICE_URL2);


        final Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.WILDCARD)
                .header("Accept", "application/geo+json")
                .header("Content-Type", "application/json, charset=utf-8")
                .header("Authorization", OPENROUTESERVICE_KEY) // send the API key for authentication
                .post(Entity.json(jsonRequestObject));



        // check the result
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        } else {

            // get the JSON response
            final String responseString = response.readEntity(String.class);
            final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();


            return jsonObject;
        }
    }


    /**
     * Method handling Dijksta request. The server client implementation is mostly taken
     * from https://www.geeksforgeeks.org/multithreaded-servers-in-java/
     * @param originLat
     * @param originLon
     * @param destinationLat
     * @param destinationLon
     * @return The query path as String.
     * @throws IOException
     */
    @Path("dijkstra")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public static String dijkstra(@QueryParam("originLat") String originLat,
                                      @QueryParam("originLon") String originLon,
                                      @QueryParam("destinationLat") String destinationLat,
                                      @QueryParam("destinationLon") String destinationLon) throws IOException {

        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));


            CoordinateRequestReader requestReader = new CoordinateRequestReader(Double.parseDouble(originLon),
                    Double.parseDouble(originLat),
                    Double.parseDouble(destinationLon),
                    Double.parseDouble(destinationLat),
                    "dijkstra");

            ObjectMapper mapper = new ObjectMapper();
            String requestString = mapper.writeValueAsString(requestReader);


            // sending the user input to server
            out.println(requestString);
            out.flush();

            // displaying server reply
            String readHere =   in.readLine();
            System.out.println("Server replied "
                    + readHere);

            final JsonObject jsonObject = Json.createReader(new StringReader(readHere)).readObject();
            return readHere;


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;



    }



    @Path("astar")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public static String astar(@QueryParam("originLat") String originLat,
                                  @QueryParam("originLon") String originLon,
                                  @QueryParam("destinationLat") String destinationLat,
                                  @QueryParam("destinationLon") String destinationLon) throws IOException {

        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));


            CoordinateRequestReader requestReader = new CoordinateRequestReader(Double.parseDouble(originLon),
                    Double.parseDouble(originLat),
                    Double.parseDouble(destinationLon),
                    Double.parseDouble(destinationLat),
                    "astar");

            ObjectMapper mapper = new ObjectMapper();
            String requestString = mapper.writeValueAsString(requestReader);


            // sending the user input to server
            out.println(requestString);
            out.flush();

            // displaying server reply
            String readHere =   in.readLine();
            System.out.println("Server replied "
                    + readHere);

            final JsonObject jsonObject = Json.createReader(new StringReader(readHere)).readObject();
            return readHere;


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;



    }


}
