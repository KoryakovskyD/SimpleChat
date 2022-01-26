package ru.avalon.javapp.devj130;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static ru.avalon.javapp.devj130.SimpleChat.clients;


public class Server {

    public Server() {
        try (ServerSocket server = new ServerSocket(ISimpleChat.SERVER_PORT, 2)) {
            System.out.println("Start server...\n");
            Socket serverSocket;

            while (true) {
                serverSocket = server.accept();
                System.out.println("Client connected!");

                NewClient newClient = new NewClient(serverSocket);
                clients.add(newClient);
                new Thread(newClient).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendMessage(String msg) {
        for (NewClient client : clients)
            client.sendMsg(msg);
    }

    public static void remove(NewClient client) {
        clients.remove(client);
    }
}
