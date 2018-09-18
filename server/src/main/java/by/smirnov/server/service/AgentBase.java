package by.smirnov.server.service;

import by.smirnov.common.connection.Connection;

public interface AgentBase {

    Connection getVacantAgent();

    void addAgent(Connection agent);

    void checkReadyState();

    boolean hasAgents();
}
