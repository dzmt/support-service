package by.smirnov.common.room.impl;

import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.handler.impl.AgentClientChatHandler;
import by.smirnov.common.handler.impl.AgentConsoleChatHandler;

public class AgentRoom implements Room {
    private Connection console;
    private Connection client;
    private volatile boolean isOpen;

    public AgentRoom(Connection console, Connection client) {
        this.console = console;
        this.client = client;
        isOpen = true;
    }

    @Override
    public void start() {
        new Thread(new AgentConsoleChatHandler(console, client, this)).start();
        new Thread(new AgentClientChatHandler(client, console, this)).start();
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void close() {
        isOpen = false;
    }

    @Override
    public Connection[] getConnections() {
        Connection[] connections = {console, client};
        return connections;
    }
}
