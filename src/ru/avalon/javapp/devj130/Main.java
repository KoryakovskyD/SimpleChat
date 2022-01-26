package ru.avalon.javapp.devj130;

public class Main {
    public static void main(String[] args) throws ChatException {
        SimpleChat server = new SimpleChat();
        server.server();
    }
}
