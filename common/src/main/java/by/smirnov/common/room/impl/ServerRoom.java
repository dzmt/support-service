package by.smirnov.common.room.impl;

import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.handler.impl.ServerAgentChatHandler;
import by.smirnov.common.handler.impl.ServerClientChatHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServerRoom implements Room {
    private Connection client;
    private Connection agent;
    private volatile boolean isOpen;

    private static final Logger logger = LogManager.getLogger(ServerRoom.class);

    public ServerRoom(Connection client, Connection agent) {
        this.client = client;
        this.agent = agent;
        isOpen = true;
    }

    @Override
    public void start() {
        new Thread(new ServerClientChatHandler(client, agent, this)).start();
        new Thread(new ServerAgentChatHandler(agent, client, this)).start();
        logger.info("Chat is started");
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
        Connection[] connections = {client, agent};
        return connections;
    }
}
