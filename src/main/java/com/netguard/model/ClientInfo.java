package com.netguard.model;

public class ClientInfo {

    private String clientName;
    private String ipAddress;
    private String connectionTime;
    private String connectionStatus;
    private String lastHeartbeat;

    public ClientInfo(
            String clientName,
            String ipAddress,
            String connectionTime,
            String connectionStatus) {

        this.clientName = clientName;
        this.ipAddress = ipAddress;
        this.connectionTime = connectionTime;
        this.connectionStatus = connectionStatus;
        this.lastHeartbeat = "Not received yet";
    }

    public String getClientName() {
        return clientName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getConnectionTime() {
        return connectionTime;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public String getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public void setLastHeartbeat(String lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
}