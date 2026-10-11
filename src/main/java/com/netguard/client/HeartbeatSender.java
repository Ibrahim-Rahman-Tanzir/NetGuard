package com.netguard.client;

import java.io.DataOutputStream;
import java.io.IOException;

public class HeartbeatSender implements Runnable {

    private final DataOutputStream output;
    private volatile boolean running = true;

    public HeartbeatSender(DataOutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {

        while (running) {

            try {

                output.writeUTF("HEARTBEAT");
                output.flush();

                System.out.println("Heartbeat sent.");

                Thread.sleep(5000);

            } catch (IOException e) {

                System.out.println(
                        "Heartbeat stopped: "
                                + e.getMessage()
                );

                running = false;

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                running = false;
            }
        }
    }

    public void stop() {

        running = false;
    }
}