package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            //Scanner sc = new Scanner(System.in);
            String line = null;


            // reading from user
            line = "be"; //TODO coordinates
            System.out.println("client sleep");
            Thread.sleep(60000);
            System.out.println("client woke up");

            // sending the user input to server
            out.println(line);
            out.flush();

            // displaying server reply
            System.out.println("Server replied "
                    + in.readLine()); //TODO:Convert this to jsonObject


            // closing the scanner object
            //sc.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}
