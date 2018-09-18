package by.smirnov.common.app;

import by.smirnov.common.connection.Connection;
import by.smirnov.common.connection.impl.ConsoleConnection;
import by.smirnov.common.connection.impl.SocketConnection;
import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.UserType;
import by.smirnov.common.room.Room;
import by.smirnov.common.room.RoomManager;
import by.smirnov.common.util.Messages;
import by.smirnov.common.util.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Application {
    private UserType type;
    private String address;
    private int port;

    private String name;
    private Connection console;
    private Connection server;

    public Application(UserType type, String address, int port) {
        this.type = type;
        this.address = address;
        this.port = port;
        console = new ConsoleConnection(System.out, new BufferedReader(new InputStreamReader(System.in)));
    }

    public void start() throws IOException {
        acceptUserName();
        createConnection();
        register();
        startChat();
    }

    private void acceptUserName() throws IOException{
        console.send(Messages.ACCEPT_NAME);
        name = console.receive().getText();
    }

    private void createConnection() throws IOException {
        Socket socket = new Socket(address, port);
        server = new SocketConnection(socket);
}

    private void register() throws IOException {
        Message register = Parser.getRegisterMessage(type, name);
        server.send(register);
        Message response = server.receive();
        console.send(response);
    }

    private void startChat() {
        Room room = RoomManager.getRoom(console, server, type);
        room.start();
    }
}
