package ru.avalon.javapp.devj130;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class SimpleChat implements ISimpleChat {

    private Socket socket;
    private String message;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    public SimpleChat(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public SimpleChat() {

    }

    @Override
    public void client() {
        System.out.println("Client started");
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Received: " + getMessage());
                } catch (ChatException ex) {
                }
            }
        }).start();

        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter message: ");
                message = scanner.nextLine();
                sendMessage(message);
                if (message.equals("exit")) {
                    System.out.println("Client exited the chat");
                    close();
                    System.exit(0);
                }
            } catch (ChatException ex) {
            }
        }
    }

    @Override
    public void server() {

        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            socket = serverSocket.accept();
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }

        System.out.println("Server started");
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Received: " + getMessage());

                    if (getMessage().equals("exit")) {
                        close();
                        break;
                    }

                } catch (ChatException ex) {
                }
            }
        }).start();

        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter message: ");
                message = scanner.nextLine();
                sendMessage(message);
            } catch (ChatException ex) {
            }
        }
    }

    @Override
    public String getMessage() throws ChatException {
        try {
            message = inputStream.readObject().toString();
        } catch (IOException | ClassNotFoundException ex) {
        }
        return message;
    }

    @Override
    public void sendMessage(String message) throws ChatException {
        try {
            outputStream.writeObject(message);
        } catch (IOException ex) {
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (IOException ex) {
        }
    }
}