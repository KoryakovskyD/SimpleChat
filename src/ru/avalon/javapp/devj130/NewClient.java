package ru.avalon.javapp.devj130;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class NewClient {

    public void run(String ip, int port, Socket socket) {
        try {
            socket = new Socket(ip, port);
            BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String message;

            while (true) {
                message=reader.readLine();
                out.println(message);
                System.out.println(in.readLine());
                if (message.equalsIgnoreCase("exit")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
