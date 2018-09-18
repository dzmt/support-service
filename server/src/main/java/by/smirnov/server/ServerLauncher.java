package by.smirnov.server;

import java.io.IOException;

public class ServerLauncher {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }
}
