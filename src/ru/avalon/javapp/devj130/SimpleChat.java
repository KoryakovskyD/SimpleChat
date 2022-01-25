package ru.avalon.javapp.devj130;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SimpleChat implements ISimpleChat{
    @Override
    public void client() throws ChatException {
        System.out.print("Server IP: ");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();
        System.out.print("\nServer port: ");
        int port = scanner.nextInt();
        System.out.println();

        try (Socket sock = new Socket(ip, port)) {
            PrintStream out = new PrintStream(sock.getOutputStream());
            System.out.print("Write message: ");
            scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            System.out.println("Client sent: " + msg);
            out.println(msg);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void server() throws ChatException {
        new Thread(() -> {
        try (ServerSocket server = new ServerSocket(SERVER_PORT, 2)) {
            System.out.println("Start server...\n");
            while (true) {
                Socket serverSock = server.accept();
                System.out.println("Client connected!");

                BufferedReader in = new BufferedReader(new InputStreamReader(serverSock.getInputStream()));
                String msg = in.readLine();
                System.out.println("Server accepted: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }).start();
    }

    @Override
    public String getMessage() throws ChatException {
        return null;
    }

    @Override
    public void sendMessage(String message) throws ChatException {

    }

    @Override
    public void close() throws ChatException {

    }
}
