package by.smirnov.common.handler;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.room.impl.ServerRoom;
import by.smirnov.common.util.Messages;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public abstract class AbstractChatHandler implements ChatHandler{
    protected Connection sender;
    protected Connection receiver;
    protected Room room;

    public AbstractChatHandler(Connection sender, Connection receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public AbstractChatHandler(Connection sender, Connection receiver, Room room) {
        this.sender = sender;
        this.receiver = receiver;
        this.room = room;
    }

    @Override
    public abstract Message handle(Message message);

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public void run() {
        while (room.isOpen()) {
            dispatch();
        }
    }

    protected Message receive() {
        Message message = null;
        try {
            message = sender.receive();
        } catch (IOException e) {
            room.close();
            message = Messages.ERROR;
        }

        return message;
    }

    protected void send(Message message) {
        try {
            receiver.send(message);
        } catch (IOException e) {
        }
    }


    protected void dispatch() {
        Message message = receive();
        if (!message.getType().equals(Type.CONTENT)){
            message = handle(message);
        }
        send(message);
    }

}
