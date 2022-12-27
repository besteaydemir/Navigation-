package org.example;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.io.StringReader;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/sysdev/orsdirections") //Resources are  defined by a path variable --check the difference
public class PathReturnResource {
    private static final String OPENROUTESERVICE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private static final String OPENROUTESERVICE_KEY = "5b3ce3597851110001cf62489c868306f2434696a351dca46f78f8f5";

//    @Consumes(MediaType.APPLICATION_JSON)
//    @Post
//
//    public String postRoute(RouteRequest body) {
//        return ""
//    } bu post için mi aaaaa

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET //Change this to post for the other one
    @Produces(MediaType.APPLICATION_JSON) // ??
    //@Consumes
    public String getIt(@QueryParam("originLat") double originLat, @QueryParam("originLon") double originLon, @QueryParam("destinationLat") double destinationLat, String destinationLon) {
        // use the jersey client api to make HTTP requests

        String startVal = originLat + "," + originLon;
        String endVal = destinationLat + "," + destinationLon;

        //@Query paramlıları burada yap

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
        }

        // get the JSON response
        final String responseString = response.readEntity(String.class);
        final JsonObject jsonObject = Json.createReader(new StringReader(responseString)).readObject();
        System.out.println("Response: " + jsonObject);

        return null;
        //return jsonObject;
    }
}
