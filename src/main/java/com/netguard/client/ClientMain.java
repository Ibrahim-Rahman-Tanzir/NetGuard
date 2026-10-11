package com.netguard.client;

import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Client Name: ");

        String clientName = scanner.nextLine();

        Client client = new Client(clientName);

        client.connectToServer();

        scanner.close();
    }
}