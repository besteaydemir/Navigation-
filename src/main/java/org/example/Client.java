package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 1234)) {
            OutputStream os =socket.getOutputStream();
            InputStream is = socket.getInputStream();

            DataOutputStream dos = new DataOutputStream(os);
            DataInputStream dis = new DataInputStream(is);

            Scanner s =new Scanner(System.in);
            while (true)
            {
                String messageFromServer = dis.readUTF();
                System.out.println(messageFromServer);

                System.out.println("Type response:");
                String messageToSend = s.nextLine();
                if (messageToSend == null || messageToSend.isEmpty())
                {
                    break;
                }
                dos.writeUTF(messageToSend);
            }

            os.close();
            is.close();
        }
    }
}
