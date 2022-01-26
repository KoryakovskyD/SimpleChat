package ru.avalon.javapp.devj130;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleChat implements ISimpleChat{
    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket socket;
    public static String ip;
    public static int port;
    private PrintStream out;

    @Override
    public void client() throws ChatException {
        System.out.print("Server IP: ");
        Scanner scanner = new Scanner(System.in);
        ip = scanner.nextLine();
        System.out.print("\nServer port: ");
        port = scanner.nextInt();
        System.out.println();

        try {
            socket = new Socket(ip, port);
            sendMessage("dddd");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void server() throws ChatException {
        try (ServerSocket server = new ServerSocket(ISimpleChat.SERVER_PORT, 2)) {
            System.out.println("Start server...\n");
            Socket serverSocket;

            while (true) {
                serverSocket = server.accept();
                System.out.println("Client connected!");
                while (true) {
                    getMessage();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getMessage() throws ChatException {
        try {

            socket = new Socket("127.0.0.1", 45678);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inMes = null;
        try {
            inMes = reader.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("getMessage=" + inMes);
        return inMes;
    }

    @Override
    public void sendMessage(String message) throws ChatException {
        try {
            socket = new Socket("127.0.0.1", 45678);
            writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println(message);
        System.out.println("sendMessage=" + message);
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
