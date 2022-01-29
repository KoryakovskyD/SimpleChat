package ru.avalon.javapp.devj130;


public class Run_server {
    public static void main(String[] args) throws ChatException {

        SimpleChat server = new SimpleChat();
        server.server();


        SimpleChat client1 = new SimpleChat();
        client1.client();
        SimpleChat client2 = new SimpleChat();
        client2.client();


        System.out.println("Sending a message: Привет");
        client1.sendMessage("Привет");
        System.out.println("Sending a message: Дарова");
        client2.sendMessage("Дарова");
        System.out.println("Sending a message: Как дела?");
        client2.sendMessage("Как дела?");
    }
}
