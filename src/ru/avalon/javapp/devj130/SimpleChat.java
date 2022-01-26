package ru.avalon.javapp.devj130;

import java.util.ArrayList;
import java.util.Scanner;

public class SimpleChat implements ISimpleChat{
    public static ArrayList<NewClient> clients = new ArrayList<>();

    @Override
    public void client() throws ChatException {
        System.out.print("Server IP: ");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();
        System.out.print("\nServer port: ");
        int port = scanner.nextInt();
        System.out.println();

        new Client(ip, port);
    }

    @Override
    public void server() throws ChatException {
        new Server();
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
