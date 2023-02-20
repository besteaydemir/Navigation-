package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Test client to test multithreading.
 */
public class ClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", 1234)) {

            System.out.println("Client asleep");
            Thread.sleep(60000);
            System.out.println("Client woke up");


        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}
