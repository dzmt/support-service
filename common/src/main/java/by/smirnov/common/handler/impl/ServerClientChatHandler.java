package by.smirnov.common.handler.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Status;
import by.smirnov.common.handler.AbstractChatHandler;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.room.impl.ServerRoom;
import by.smirnov.common.util.Messages;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServerClientChatHandler extends AbstractChatHandler {
    private static final Logger logger = LogManager.getLogger(ServerClientChatHandler.class);

    public ServerClientChatHandler(Connection sender, Connection receiver) {
        super(sender, receiver);
    }

    public ServerClientChatHandler(Connection sender, Connection receiver, Room room) {
        super(sender, receiver, room);
    }

    @Override
    public Message handle(Message message) {
        switch (message.getType()) {
            case LEAVE: {
                room.close();
                logger.info("Client leave chat.");
                return Messages.LEAVE;
            }
            case EXIT:
                sender.setStatus(Status.INACTIVE);
                room.close();
                logger.info("Client exit from system.");
                return Messages.EXIT;
            case ERROR:{
                sender.setStatus(Status.INACTIVE);
                room.close();
                logger.info("Error is occured on the client-side");
                return Messages.ERROR_CLIENT;
            }
        }
        return message;
    }

}
