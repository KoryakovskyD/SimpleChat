package ru.avalon.javapp.devj130;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String ip;
    private static int port;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, write who are you: server or client...");
        String mode = scanner.nextLine();

        if (mode.equals("server")) {
            System.out.println("Waiting...");
            SimpleChat simpleChatServer = new SimpleChat();
            simpleChatServer.server();
        } else {
            System.out.print("Server IP: ");
            ip = scanner.nextLine();
            System.out.print("\nServer port: ");
            port = scanner.nextInt();
            System.out.println();

            SimpleChat simpleChatClient = new SimpleChat(ip, port);
            simpleChatClient.server();
        }
    }
}