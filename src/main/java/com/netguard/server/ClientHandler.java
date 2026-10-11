package com.netguard.server;

import com.netguard.model.ClientInfo;

import java.io.DataInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final Server server;
    private final ServerGUI gui;
    private final SecurityMonitor securityMonitor;

    // =========================================================
    // OLD CONSTRUCTOR
    // Keeps old code compatible
    // =========================================================

    public ClientHandler(
            Socket clientSocket,
            Server server) {

        this.clientSocket = clientSocket;
        this.server = server;
        this.gui = null;

        this.securityMonitor =
                server.getSecurityMonitor();
    }

    // =========================================================
    // NEW CONSTRUCTOR
    // Used by GUI-enabled Server
    // =========================================================

    public ClientHandler(
            Socket clientSocket,
            Server server,
            ServerGUI gui) {

        this.clientSocket = clientSocket;
        this.server = server;
        this.gui = gui;

        this.securityMonitor =
                server.getSecurityMonitor();
    }

    @Override
    public void run() {

        ClientInfo clientInfo = null;

        try {

            DataInputStream input =
                    new DataInputStream(
                            clientSocket.getInputStream()
                    );

            // =================================================
            // RECEIVE CLIENT NAME
            // =================================================

            String clientName =
                    input.readUTF();

            String clientIP =
                    clientSocket
                            .getInetAddress()
                            .getHostAddress();

            String connectionTime =
                    LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd HH:mm:ss"
                            )
                    );

            // =================================================
            // SECURITY MONITOR
            // =================================================

            securityMonitor.checkConnection(
                    clientName
            );

            // =================================================
            // CREATE CLIENT INFO
            // =================================================

            clientInfo =
                    new ClientInfo(
                            clientName,
                            clientIP,
                            connectionTime,
                            "ONLINE"
                    );

            // =================================================
            // ADD CLIENT TO SERVER
            // =================================================

            server.addClient(clientInfo);

            // =================================================
            // UPDATE GUI
            // =================================================

            if (gui != null) {

                gui.addClient(
                        clientName,
                        clientIP,
                        connectionTime,
                        "ONLINE",
                        "Not received yet"
                );

                gui.addActivity(
                        "CONNECTED",
                        "Client "
                                + clientName
                                + " connected from "
                                + clientIP
                );
            }

            System.out.println();

            System.out.println(
                    "Waiting for heartbeat from "
                            + clientName
                            + "..."
            );

            // =================================================
            // RECEIVE HEARTBEATS
            // =================================================

            while (true) {

                String message =
                        input.readUTF();

                if ("HEARTBEAT".equals(message)) {

                    String heartbeatTime =
                            LocalDateTime.now().format(
                                    DateTimeFormatter.ofPattern(
                                            "yyyy-MM-dd HH:mm:ss"
                                    )
                            );

                    // Update client info
                    clientInfo.setLastHeartbeat(
                            heartbeatTime
                    );

                    clientInfo.setConnectionStatus(
                            "ONLINE"
                    );

                    System.out.println();

                    System.out.println(
                            "Heartbeat received from "
                                    + clientName
                    );

                    server.printClientStatus(
                            clientInfo
                    );

                    // =================================================
                    // UPDATE GUI
                    // =================================================

                    if (gui != null) {

                        gui.heartbeatReceived(
                                clientName,
                                heartbeatTime
                        );

                        gui.addActivity(
                                "HEARTBEAT",
                                "Heartbeat received from "
                                        + clientName
                        );
                    }
                }
            }

        } catch (Exception e) {

            // =================================================
            // CLIENT DISCONNECTED
            // =================================================

            if (clientInfo != null) {

                clientInfo.setConnectionStatus(
                        "OFFLINE"
                );

                System.out.println();

                System.out.println(
                        "Client disconnected: "
                                + clientInfo.getClientName()
                );

                System.out.println(
                        "Status changed to OFFLINE"
                );

                // =================================================
                // SECURITY MONITOR
                // =================================================

                securityMonitor.recordDisconnect(
                        clientInfo.getClientName()
                );

                // =================================================
                // GUI OFFLINE
                // =================================================

                if (gui != null) {

                    gui.clientOffline(
                            clientInfo.getClientName()
                    );

                    gui.addActivity(
                            "OFFLINE",
                            "Client "
                                    + clientInfo
                                    .getClientName()
                                    + " disconnected."
                    );
                }
            }

        } finally {

            try {

                clientSocket.close();

            } catch (Exception ignored) {

            }
        }
    }
}