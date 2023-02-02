package org.example.alternatives;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                InputStream is = clientSocket.getInputStream();
                OutputStream os = clientSocket.getOutputStream();

                DataOutputStream dos = new DataOutputStream(os);
                DataInputStream dis = new DataInputStream(is);

                dos.writeUTF("You are connected");

                Scanner s = new Scanner(System.in);
                while (true)
                {
                    String messageFromClient = dis.readUTF();
                    System.out.println(messageFromClient);

                    System.out.println("Type response:");
                    String messageToSend = s.nextLine();
                    if (messageToSend == null || messageToSend.isEmpty())
                    {
                        break;
                    }
                    dos.writeUTF(messageToSend);
                }

                is.close();
                os.close();
                clientSocket.close();
            }
        }


    }


}
