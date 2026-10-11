package com.netguard.server;

import java.util.HashMap;
import java.util.Map;

public class SecurityMonitor {

    private final Map<String, Integer> connectionAttempts =
            new HashMap<>();

    private final Map<String, Integer> disconnectCount =
            new HashMap<>();

    private static final int WARNING_LIMIT = 3;

    private final LogManager logManager =
            new LogManager();

    private final ServerGUI gui;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public SecurityMonitor(ServerGUI gui) {

        this.gui = gui;
    }

    // =========================================================
    // CHECK NEW CLIENT CONNECTION
    // =========================================================

    public synchronized void checkConnection(
            String clientName) {

        System.out.println();
        System.out.println(
                "Security Monitor: Checking client "
                        + clientName
        );

        if (clientName == null ||
                clientName.trim().isEmpty()) {

            System.out.println(
                    "⚠ SECURITY WARNING!"
            );

            System.out.println(
                    "Unknown client detected!"
            );

            logManager.logWarning(
                    "Unknown client detected."
            );

            return;
        }

        // =================================================
        // COUNT CONNECTION ATTEMPTS
        // =================================================

        int attempts =
                connectionAttempts.getOrDefault(
                        clientName,
                        0
                );

        attempts++;

        connectionAttempts.put(
                clientName,
                attempts
        );

        System.out.println(
                "Connection Attempts: "
                        + attempts
        );

        // =================================================
        // DETECT REPEATED CONNECTION ATTEMPTS
        // =================================================

        if (attempts >= WARNING_LIMIT) {

            String alertMessage =
                    "Multiple connection attempts detected. "
                            + "Client: "
                            + clientName;

            System.out.println();
            System.out.println(
                    "⚠⚠ SECURITY ALERT ⚠⚠"
            );

            System.out.println(
                    "Suspicious connection behavior detected!"
            );

            System.out.println(
                    "Client: "
                            + clientName
            );

            System.out.println(
                    "Multiple connection attempts detected."
            );

            // Save security alert
            logManager.logSecurityAlert(
                    alertMessage
            );

            // Send alert to GUI
            if (gui != null) {

                gui.securityAlert(
                        alertMessage
                );
            }

        } else {

            System.out.println(
                    "✓ Client verified: "
                            + clientName
            );
        }
    }

    // =========================================================
    // RECORD CLIENT DISCONNECTION
    // =========================================================

    public synchronized void recordDisconnect(
            String clientName) {

        if (clientName == null ||
                clientName.trim().isEmpty()) {

            return;
        }

        int count =
                disconnectCount.getOrDefault(
                        clientName,
                        0
                );

        count++;

        disconnectCount.put(
                clientName,
                count
        );

        System.out.println();

        System.out.println(
                "Security Monitor: Client disconnected"
        );

        System.out.println(
                "Client: "
                        + clientName
        );

        System.out.println(
                "Disconnect Count: "
                        + count
        );

        // =================================================
        // DETECT FREQUENT DISCONNECT BEHAVIOR
        // =================================================

        if (count >= WARNING_LIMIT) {

            String alertMessage =
                    "Frequent connect/disconnect behavior detected. "
                            + "Client: "
                            + clientName;

            System.out.println();

            System.out.println(
                    "⚠⚠ SECURITY ALERT ⚠⚠"
            );

            System.out.println(
                    "Frequent connect/disconnect behavior detected!"
            );

            System.out.println(
                    "Client: "
                            + clientName
            );

            // Save security alert
            logManager.logSecurityAlert(
                    alertMessage
            );

            // Send alert to GUI
            if (gui != null) {

                gui.securityAlert(
                        alertMessage
                );
            }
        }
    }
}