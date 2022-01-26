package ru.avalon.javapp.devj130;

import java.io.*;
import java.net.Socket;

public class NewClient implements Runnable {

    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket clientSocket = null;

    public NewClient(Socket socket) {
        try {
            this.clientSocket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (reader != null) {
                    String clientMessage = reader.readLine();
                    if (clientMessage.contains("вышел из чата!")) {
                        break;
                    }
                    Server.sendMessage(clientMessage);
                }
                Thread.sleep(100);
            }

        }
        catch (InterruptedException |  IOException ex) {
            ex.printStackTrace();
        }
        finally {
            this.close();
        }
    }

    public void sendMsg(String msg) {
        try {
            writer.append(msg);
            writer.newLine();
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        Server.remove(this);
    }
}
