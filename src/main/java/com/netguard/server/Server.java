package com.netguard.server;

import com.netguard.model.ClientInfo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT = 5000;

    private final List<ClientInfo> clients =
            new ArrayList<>();

    private final ServerGUI gui;

    private final SecurityMonitor securityMonitor;

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // Keeps ServerMain compatible
    // =========================================================

    public Server() {

        this.gui = null;

        this.securityMonitor =
                new SecurityMonitor(null);
    }

    // =========================================================
    // GUI CONSTRUCTOR
    // Used when Server is started from ServerGUI
    // =========================================================

    public Server(ServerGUI gui) {

        this.gui = gui;

        this.securityMonitor =
                new SecurityMonitor(gui);
    }

    // =========================================================
    // GET SECURITY MONITOR
    // =========================================================

    public SecurityMonitor getSecurityMonitor() {

        return securityMonitor;
    }

    // =========================================================
    // START SERVER
    // =========================================================

    public void startServer() {

        try {

            ServerSocket serverSocket =
                    new ServerSocket(PORT);

            System.out.println();
            System.out.println(
                    "================================"
            );

            System.out.println(
                    "        NetGuard Server"
            );

            System.out.println(
                    "================================"
            );

            System.out.println(
                    "Server started successfully."
            );

            System.out.println(
                    "Port: " + PORT
            );

            System.out.println(
                    "Waiting for clients..."
            );

            // Tell GUI server is online
            if (gui != null) {

                gui.setServerRunning(true);

                gui.addActivity(
                        "SYSTEM",
                        "NetGuard server started on port "
                                + PORT
                );
            }

            // =================================================
            // HEARTBEAT MANAGER
            // =================================================

            HeartbeatManager heartbeatManager =
                    new HeartbeatManager(clients);

            Thread heartbeatThread =
                    new Thread(
                            heartbeatManager,
                            "NetGuard-Heartbeat-Manager"
                    );

            heartbeatThread.setDaemon(true);

            heartbeatThread.start();

            // =================================================
            // ACCEPT CLIENTS
            // =================================================

            while (true) {

                Socket clientSocket =
                        serverSocket.accept();

                System.out.println();
                System.out.println(
                        "New client connection received."
                );

                if (gui != null) {

                    gui.addActivity(
                            "NETWORK",
                            "New client connection received."
                    );
                }

                ClientHandler clientHandler =
                        new ClientHandler(
                                clientSocket,
                                this,
                                gui
                        );

                Thread clientThread =
                        new Thread(
                                clientHandler,
                                "NetGuard-Client-"
                                        + clientSocket
                                        .getPort()
                        );

                clientThread.start();
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Server Error: "
                            + e.getMessage()
            );

            if (gui != null) {

                gui.setServerRunning(false);

                gui.addActivity(
                        "ERROR",
                        "Server error: "
                                + e.getMessage()
                );
            }
        }
    }

    // =========================================================
    // ADD CLIENT
    // =========================================================

    public synchronized void addClient(
            ClientInfo clientInfo) {

        clients.add(clientInfo);

        printClientList();
    }

    // =========================================================
    // GET CLIENTS
    // =========================================================

    public synchronized List<ClientInfo> getClients() {

        return new ArrayList<>(clients);
    }

    // =========================================================
    // PRINT CLIENT LIST
    // =========================================================

    public synchronized void printClientList() {

        System.out.println();

        System.out.println(
                "========== Connected Clients =========="
        );

        int number = 1;

        for (ClientInfo client : clients) {

            System.out.println(
                    number + ". "
                            + client.getClientName()
            );

            System.out.println(
                    "   IP: "
                            + client.getIpAddress()
            );

            System.out.println(
                    "   Connection Time: "
                            + client.getConnectionTime()
            );

            System.out.println(
                    "   Status: "
                            + client.getConnectionStatus()
            );

            System.out.println(
                    "   Last Heartbeat: "
                            + client.getLastHeartbeat()
            );

            System.out.println();

            number++;
        }

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Total Connected Clients: "
                        + clients.size()
        );
    }

    // =========================================================
    // PRINT CLIENT STATUS
    // =========================================================

    public synchronized void printClientStatus(
            ClientInfo client) {

        System.out.println();

        System.out.println(
                "Client: "
                        + client.getClientName()
                        + " | IP: "
                        + client.getIpAddress()
                        + " | Status: "
                        + client.getConnectionStatus()
                        + " | Last Heartbeat: "
                        + client.getLastHeartbeat()
        );
    }
}