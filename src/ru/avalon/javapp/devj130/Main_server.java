package ru.avalon.javapp.devj130;

public class Main_server {
    public static void main(String[] args) throws ChatException {
        SimpleChat server = new SimpleChat();
        server.server();
    }
}
