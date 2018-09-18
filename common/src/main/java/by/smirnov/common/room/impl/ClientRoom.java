package by.smirnov.common.room.impl;

import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.handler.impl.ClientAgentChatHandler;
import by.smirnov.common.handler.impl.ClientConsoleChatHandler;

public class ClientRoom implements Room {
    private Connection console;
    private Connection agent;
    private volatile boolean isOpen;

    public ClientRoom(Connection console, Connection agent) {
        this.console = console;
        this.agent = agent;
        isOpen = true;
    }

    @Override
    public void start() {
        new Thread(new ClientConsoleChatHandler(console, agent, this)).start();
        new Thread(new ClientAgentChatHandler(agent, console, this)).start();
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
        Connection[] connections = {console, agent};
        return connections;
    }
}
