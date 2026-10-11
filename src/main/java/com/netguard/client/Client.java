package com.netguard.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 5000;

    private final String clientName;

    public Client(String clientName) {
        this.clientName = clientName;
    }

    public void connectToServer() {

        try {

            Socket socket = new Socket(SERVER_IP, PORT);

            DataOutputStream output =
                    new DataOutputStream(socket.getOutputStream());

            // Send client name to server
            output.writeUTF(clientName);
            output.flush();

            System.out.println("Connected to NetGuard Server!");
            System.out.println("Client Name: " + clientName);

            // Start Heartbeat
            HeartbeatSender heartbeatSender =
                    new HeartbeatSender(output);

            Thread heartbeatThread =
                    new Thread(heartbeatSender);

            heartbeatThread.start();

        } catch (IOException e) {

            System.out.println(
                    "Client Error: " + e.getMessage()
            );
        }
    }
}