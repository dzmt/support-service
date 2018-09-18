package by.smirnov.common.handler.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.handler.AbstractChatHandler;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.util.Messages;

import java.io.IOException;

public class AgentClientChatHandler extends AbstractChatHandler{

    public AgentClientChatHandler(Connection sender, Connection receiver) {
        super(sender, receiver);
    }

    public AgentClientChatHandler(Connection sender, Connection receiver, Room room) {
        super(sender, receiver, room);
    }

    @Override
    public Message handle(Message message) {
        switch (message.getType()) {
            case EXIT: {
                try {
                    sender.send(Messages.OK_EXIT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Messages.CLIENT_LEAVE;
            }
            case LEAVE: {
                try {
                    sender.send(Messages.OK_LEAVE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Messages.CLIENT_LEAVE;
            }
            case OK_EXIT:{
                return Messages.GOODBYE;
            }
            case ERROR_CLIENT:{
                try {
                    sender.send(Messages.ERROR_CLIENT);
                } catch (IOException e) {
                }
                return Messages.CLIENT_LEAVE;
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
