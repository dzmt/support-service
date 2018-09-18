package by.smirnov.common.handler.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Status;
import by.smirnov.common.handler.AbstractChatHandler;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.util.Messages;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServerAgentChatHandler extends AbstractChatHandler {
    private static final Logger logger = LogManager.getLogger(ServerClientChatHandler.class);

    public ServerAgentChatHandler(Connection sender, Connection receiver) {
        super(sender, receiver);
    }

    public ServerAgentChatHandler(Connection sender, Connection receiver, Room room) {
        super(sender, receiver, room);
    }

    @Override
    public Message handle(Message message) {
        switch (message.getType()) {
            case EXIT:
                sender.setStatus(Status.INACTIVE);
                room.close();
                logger.info("Agent has exited from chat and system.");
                return Messages.EXIT;
            case ERROR:{
                sender.setStatus(Status.INACTIVE);
                room.close();
                logger.info("Error is occured on the agent-side.");
                return Messages.ERROR_AGENT;
            }
        }
        return message;
    }
}
