package org.example;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.io.StringReader;


public class APIPath {

    private static final String OPENROUTESERVICE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private static final String OPENROUTESERVICE_KEY = "5b3ce3597851110001cf62489c868306f2434696a351dca46f78f8f5";

    public static void poiSearch(String startval, String endval) {
        // create a json object which we will send in the post request
        // {
        //      format_in: "point",
        //      geometry: [lon, lat]
        // }
        final JsonObject request = Json.createObjectBuilder()
                .add("format_in", "point")
                .add(
                        "geometry",
                        Json.createArrayBuilder()
                                .add(startval)
                                .add(endval)
                                .build()
                )
                .build();

        // use the jersey client api to make HTTP requests
        final JerseyClient client = new JerseyClientBuilder().build();
        final JerseyWebTarget webTarget = client.target(OPENROUTESERVICE_URL);
        final Response response = webTarget
                .queryParam("start", startval)
                .queryParam("end", endval)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", OPENROUTESERVICE_KEY) // send the API key for authentication
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();


        // check the result
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed: HTTP error code: " + response.getStatus());
        }

        // get the JSON response
        final String responseString = response.readEntity(String.class);
        final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();
        System.out.println("Response: " + jsonObject);

        // Extract the elevation -- the shortest path query
//        final JsonNumber elevation = jsonObject
//                .getJsonObject("geometry")
//                .getJsonArray("coordinates")
//                .getJsonNumber(2);
//        System.out.println("Elevation: " + elevation.doubleValue() + "m");
    }

    public static void main(String[] args) {
        poiSearch("8.687872,49.420318", "8.681495,49.41461");
    }
}



