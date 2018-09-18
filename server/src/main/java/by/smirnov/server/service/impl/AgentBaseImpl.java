package by.smirnov.server.service.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.handler.impl.ServerClientChatHandler;
import by.smirnov.common.util.Messages;
import by.smirnov.server.service.AgentBase;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class AgentBaseImpl implements AgentBase {
    private static final Logger logger = LogManager.getLogger(ServerClientChatHandler.class);

    private ConcurrentLinkedQueue<Connection> waitingAgents = new ConcurrentLinkedQueue<>();

    @Override
    public Connection getVacantAgent() {
        return waitingAgents.poll();
    }

    @Override
    public void addAgent(Connection agent) {
        waitingAgents.add(agent);
    }

    @Override
    public void checkReadyState() {
        List<Connection> exited = waitingAgents
                .stream()
                .filter(agent -> {
                    boolean predicate = false;
                    try {
                        if (agent.isAvailable()) {
                            Message message = agent.receive();
                            if (message.getType().equals(Type.EXIT)) {
                                predicate = true;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return predicate;
                })
                .collect(Collectors.toList());
        waitingAgents.removeAll(exited);
        exited.forEach(agent -> {
            try {
                agent.send(Messages.OK_EXIT);
                agent.close();
                logger.info("Agent exit from the agent base.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean hasAgents() {
        return waitingAgents.size() > 0;
    }
}
