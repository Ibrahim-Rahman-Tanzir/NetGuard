package com.netguard.server;

import com.netguard.model.ClientInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HeartbeatManager implements Runnable {

    private final List<ClientInfo> clients;

    private static final int TIMEOUT_SECONDS = 10;

    private volatile boolean running = true;

    public HeartbeatManager(List<ClientInfo> clients) {
        this.clients = clients;
    }

    @Override
    public void run() {

        while (running) {

            try {

                Thread.sleep(5000);

                checkClients();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                running = false;
            }
        }
    }

    private void checkClients() {

        LocalDateTime now =
                LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        synchronized (clients) {

            for (ClientInfo client : clients) {

                String lastHeartbeat =
                        client.getLastHeartbeat();

                if (lastHeartbeat.equals(
                        "Not received yet")) {

                    continue;
                }

                LocalDateTime lastTime =
                        LocalDateTime.parse(
                                lastHeartbeat,
                                formatter
                        );

                long seconds =
                        java.time.Duration.between(
                                lastTime,
                                now
                        ).getSeconds();

                if (seconds > TIMEOUT_SECONDS) {

                    if (!client.getConnectionStatus()
                            .equals("OFFLINE")) {

                        client.setConnectionStatus(
                                "OFFLINE"
                        );

                        System.out.println();
                        System.out.println(
                                "⚠ Client Offline: "
                                        + client.getClientName()
                        );
                    }

                } else {

                    client.setConnectionStatus(
                            "ONLINE"
                    );
                }
            }
        }
    }

    public void stop() {

        running = false;
    }
}