package ru.avalon.javapp.devj130;

public class Main {
    public static void main(String[] args) throws ChatException {
        SimpleChat server = new SimpleChat();
        server.server();

        SimpleChat client1 = new SimpleChat();
        client1.client();
        SimpleChat client2 = new SimpleChat();
        client2.client();
    }
}