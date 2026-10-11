package com.netguard.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {

    private static final String LOG_FOLDER =
            "resources/logs";

    private static final String LOG_FILE =
            LOG_FOLDER + File.separator + "activity.log";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );

    public LogManager() {

        createLogFolder();

        System.out.println(
                "LogManager initialized. Log path: "
                        + new File(LOG_FILE).getAbsolutePath()
        );
    }

    // Create logs folder
    private void createLogFolder() {

        File folder =
                new File(LOG_FOLDER);

        if (!folder.exists()) {

            boolean created =
                    folder.mkdirs();

            System.out.println(
                    "Log folder created: "
                            + created
            );
        }
    }

    // Write activity to log file
    public synchronized void log(
            String message) {

        try {

            File folder =
                    new File(LOG_FOLDER);

            if (!folder.exists()) {

                folder.mkdirs();
            }

            File logFile =
                    new File(LOG_FILE);

            FileWriter fileWriter =
                    new FileWriter(
                            logFile,
                            true
                    );

            PrintWriter writer =
                    new PrintWriter(
                            fileWriter
                    );

            String time =
                    LocalDateTime.now()
                            .format(FORMATTER);

            writer.println(
                    "[" + time + "] "
                            + message
            );

            writer.flush();
            writer.close();

            System.out.println(
                    "Log saved successfully: "
                            + logFile.getAbsolutePath()
            );

        } catch (IOException e) {

            System.out.println(
                    "Log Error: "
                            + e.getMessage()
            );
        }
    }

    // Client connected
    public void logConnection(
            String clientName,
            String ipAddress) {

        log(
                "CLIENT CONNECTED | Name: "
                        + clientName
                        + " | IP: "
                        + ipAddress
        );
    }

    // Client disconnected
    public void logDisconnection(
            String clientName) {

        log(
                "CLIENT DISCONNECTED | Name: "
                        + clientName
        );
    }

    // Security warning
    public void logWarning(
            String message) {

        log(
                "SECURITY WARNING | "
                        + message
        );
    }

    // Security alert
    public void logSecurityAlert(
            String message) {

        log(
                "SECURITY ALERT | "
                        + message
        );
    }
}