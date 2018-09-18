package by.smirnov.common.handler.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.handler.AbstractChatHandler;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.util.Messages;

import java.io.IOException;

public class AgentConsoleChatHandler extends AbstractChatHandler {
    public AgentConsoleChatHandler(Connection sender, Connection receiver) {
        super(sender, receiver);
    }

    public AgentConsoleChatHandler(Connection sender, Connection receiver, Room room) {
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
                try {
                    sender.send(Messages.NOT_SUPPORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }
}
