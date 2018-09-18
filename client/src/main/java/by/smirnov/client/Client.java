package by.smirnov.client;

import by.smirnov.common.app.Application;
import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;
import by.smirnov.common.enumeration.UserType;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.connection.impl.ConsoleConnection;
import by.smirnov.common.connection.impl.SocketConnection;
import by.smirnov.common.room.impl.ClientRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Application{
    public Client(UserType type, String address, int port) {
        super(type, address, port);
    }
}
