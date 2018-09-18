package by.smirnov.client;

import by.smirnov.common.enumeration.UserType;

import java.io.IOException;

public class ClientLauncher {
    public static void main(String[] args){
        Client client = new Client(UserType.CLIENT, "188.225.56.206", 9999);

        try {
            client.start();
        } catch (IOException e) {
            System.out.println("Sorry. Could not connect to the server. Please, try later.");
        }
    }
}
