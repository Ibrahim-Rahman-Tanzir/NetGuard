# NetGuard
### Intelligent Network Security Monitoring System

NetGuard is a Java-based client-server application developed to explore TCP socket communication and real-time client connectivity monitoring. It uses heartbeat signals to track client availability and provides graphical interfaces for server-side monitoring and client interaction.

The project demonstrates practical networking concepts through a modular Java application built with Maven.

## Features

- **TCP Communication** — Socket-based communication between server and clients.
- **Heartbeat Monitoring** — Periodic heartbeat signals for monitoring client connectivity.
- **Connection Status Detection** — Identifies whether a client is online or disconnected.
- **Server Dashboard** — Graphical interface for server-side monitoring.
- **Client Interface** — Desktop interface for client interaction.
- **Multi-Client Handling** — Supports multiple client connections.

## Technology Stack

| Component | Technology |
|---|---|
| Language | Java |
| User Interface | Java Swing |
| Networking | TCP Sockets |
| Build and Dependencies | Maven |
| Development Environment | IntelliJ IDEA |
| JDK | Java 21 |

## Architecture

NetGuard follows a client-server model.

- **Server:** Accepts client connections and coordinates connection monitoring.
- **Client:** Connects to the server and participates in heartbeat monitoring.
- **Model:** Represents client information.
- **Utilities:** Provides shared constants and logging functionality.

## Project Structure

```text
src/main/java/com/netguard/
├── client/
│   ├── Client.java
│   ├── ClientGUI.java
│   ├── ClientMain.java
│   └── HeartbeatSender.java
├── model/
│   └── ClientInfo.java
├── server/
│   ├── ClientHandler.java
│   ├── HeartbeatManager.java
│   ├── LogManager.java
│   ├── SecurityMonitor.java
│   ├── Server.java
│   ├── ServerGUI.java
│   └── ServerMain.java
└── utils/
    ├── Constants.java
    └── Logger.java
```

## Getting Started

### Prerequisites

- JDK 21
- IntelliJ IDEA or another Java IDE with Maven support

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/Ibrahim-Rahman-Tanzir/NetGuard.git
   ```

2. Open the project in IntelliJ IDEA.
3. Allow Maven to resolve the project dependencies.
4. Run `ServerMain.java`.
5. Run `ClientMain.java` and verify the server connection settings.

Start the server before launching clients. The configured host and port must be reachable by the client.

## Screenshots

Application screenshots will be added here to demonstrate the server monitoring interface and client interface.

## Project Context

NetGuard was developed as a Computer Networking project to apply concepts of TCP communication, client-server architecture, and heartbeat-based connection monitoring in Java.

## Author

**Ibrahim Rahman**  
Computer Science and Engineering  
Green University of Bangladesh
