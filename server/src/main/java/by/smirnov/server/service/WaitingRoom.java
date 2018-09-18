package by.smirnov.server.service;

import by.smirnov.common.connection.Connection;

import java.util.List;

public interface WaitingRoom {

    List<Connection> findReadyClients();

    void addClient(Connection client);
}
