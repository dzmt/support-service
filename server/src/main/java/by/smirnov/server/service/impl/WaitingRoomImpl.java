package by.smirnov.server.service.impl;

import by.smirnov.common.connection.Connection;
import by.smirnov.server.service.WaitingRoom;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class WaitingRoomImpl implements WaitingRoom {
     private ConcurrentLinkedQueue<Connection> waitingClients = new ConcurrentLinkedQueue<>();

    @Override
    public List<Connection> findReadyClients() {
        return findActiveConnection(waitingClients);
    }


    @Override
    public void addClient(Connection client) {
        waitingClients.add(client);
    }

    private List<Connection> findActiveConnection(ConcurrentLinkedQueue<Connection> queue) {
        List<Connection> connections = queue
                .stream()
                .filter(client -> {
                    boolean available = false;
                    try {
                        available = client.isAvailable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return available;
                })
                .collect(Collectors.toList());
        queue.removeAll(connections);
        return connections;
    }
}
