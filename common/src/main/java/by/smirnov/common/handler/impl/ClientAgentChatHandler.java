package by.smirnov.common.handler.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.handler.AbstractChatHandler;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.util.Messages;

import java.io.IOException;

public class ClientAgentChatHandler extends AbstractChatHandler {
    public ClientAgentChatHandler(Connection sender, Connection receiver) {
        super(sender, receiver);
    }

    public ClientAgentChatHandler(Connection sender, Connection receiver, Room room) {
        super(sender, receiver, room);
    }

    @Override
    public Message handle(Message message) {
        switch (message.getType()) {
            case EXIT: {
                try {
                    sender.send(Messages.OK_EXIT);
                } catch (IOException e) {
                    return message = Messages.ERROR_SERVER;
                }
                return Messages.AGENT_EXITED;
            }
            case OK_LEAVE: {
                return Messages.ASK_QUESTION;
            }
            case OK_EXIT:{
                return Messages.GOODBYE;
            }
            case ERROR_AGENT:{
                try {
                    sender.send(Messages.ERROR_AGENT);
                } catch (IOException e) {
                }
                return Messages.AGENT_EXITED;
            }
            case ERROR:{
                try {
                    room.close();
                    receiver.send(Messages.ERROR_SERVER);
                } catch (IOException e) {
                }
            }
        }
        return message;
    }
}
