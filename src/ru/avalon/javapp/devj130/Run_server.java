package ru.avalon.javapp.devj130;

public class Run_server {
    public static void main(String[] args) throws ChatException {
        new Thread(() -> {
            SimpleChat server = new SimpleChat();
            try {
                server.server();
            } catch (ChatException e) {
                e.printStackTrace();
            }
        }).start();

        SimpleChat client1 = new SimpleChat();
        client1.client();
        SimpleChat client2 = new SimpleChat();
        client2.client();


        client1.sendMessage("Привет");
        client2.getMessage();
        client2.sendMessage("Дарова");
        client1.getMessage();
    }
}
