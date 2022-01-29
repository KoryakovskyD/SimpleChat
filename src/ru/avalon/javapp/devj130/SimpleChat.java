package ru.avalon.javapp.devj130;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleChat implements ISimpleChat{
    private BufferedReader reader;
    private BufferedWriter writer;
    public static String ip;
    public static int port;

    @Override
    public void client() throws ChatException {
        /*
        System.out.print("Server IP: ");
        Scanner scanner = new Scanner(System.in);
        ip = scanner.nextLine();
        System.out.print("\nServer port: ");
        port = scanner.nextInt();
        System.out.println();
        */

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ip="127.0.0.1";
        port=45678;

        try {
            Socket socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void server() throws ChatException {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(ISimpleChat.SERVER_PORT, 2)) {
                //System.out.println("Start server...\n");
                Socket serverSocket;

                while (true) {
                    serverSocket = server.accept();
                    System.out.println("Client connected!");

                    try {
                        reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                        writer = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    new Thread(() -> {
                        String message = null;
                        while (true) {
                            try {
                                message = reader.readLine();
                                if (message != null)
                                    System.out.println("The server has received the message, forwarding it to another user: " + message);
                                    sendMessage(message);
                            } catch (IOException | ChatException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    close();
                } catch (ChatException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public String getMessage() throws ChatException {
        String msg;
        try {
            msg = reader.readLine();
            if (msg != null) {
                return msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMessage(String message) throws ChatException {
        try {
            writer.append(message);
            writer.newLine();
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
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
