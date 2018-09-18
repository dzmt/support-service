package by.smirnov.common.handler.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.handler.AbstractChatHandler;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.util.Messages;

public class ClientConsoleChatHandler extends AbstractChatHandler{
    public ClientConsoleChatHandler(Connection sender, Connection receiver) {
        super(sender, receiver);
    }

    public ClientConsoleChatHandler(Connection sender, Connection receiver, Room room) {
        super(sender, receiver, room);
    }

    @Override
    public Message handle(Message message) {
        switch (message.getType()) {
            case EXIT: {
                room.close();
                return Messages.EXIT;
            }
            case LEAVE: {
                return Messages.LEAVE;
            }
        }
        return message;
    }
}
