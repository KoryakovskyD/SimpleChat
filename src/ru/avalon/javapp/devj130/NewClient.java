package ru.avalon.javapp.devj130;

import java.io.*;
import java.net.Socket;

public class NewClient implements Runnable{
    private Socket clientSocket = null;
    private BufferedReader reader;
    private BufferedWriter writer;
    private SimpleChat simpleChat;

    public NewClient(Socket socket) {
        this.clientSocket = socket;
        try {
            reader  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (reader != null) {
                try {
                    String message = reader.readLine();
                    System.out.println("str=" + message);
                    simpleChat.sendMessage(message);
                    Thread.sleep(200);
                } catch (IOException | InterruptedException | ChatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(String message) {
        System.out.println("Sending a message: " + message);
        try {
            writer.append(message);
            writer.newLine();
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getMessage() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
