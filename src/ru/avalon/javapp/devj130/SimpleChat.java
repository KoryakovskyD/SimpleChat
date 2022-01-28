package ru.avalon.javapp.devj130;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleChat implements ISimpleChat{
    private static ArrayList<NewClient> clients = new ArrayList<>();
    private BufferedReader reader;
    private BufferedWriter writer;
    public static String ip;
    public static int port;

    @Override
    public void client() throws ChatException {
        System.out.print("Server IP: ");
        Scanner scanner = new Scanner(System.in);
        ip = scanner.nextLine();
        System.out.print("\nServer port: ");
        port = scanner.nextInt();
        System.out.println();

        try {
            Socket socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            String message = null;
            while (true) {
                try {
                    message = reader.readLine();
                    if (message != null)
                        System.out.println("Another user sent: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void server() throws ChatException {
        try (ServerSocket server = new ServerSocket(ISimpleChat.SERVER_PORT, 2)) {
            //System.out.println("Start server...\n");
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
        } finally {
            close();
        }
    }


    @Override
    public String getMessage() throws ChatException {
            for (NewClient o : clients) {
                o.getMessage();
            }
        return null;
    }

    @Override
    public void sendMessage(String message) throws ChatException {
        for (NewClient o : clients) {
            o.sendMessage(message);
        }
    }

    @Override
    public void close() throws ChatException {
        try {
            writer.flush();
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
