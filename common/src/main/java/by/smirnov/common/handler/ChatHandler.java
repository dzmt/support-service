package by.smirnov.common.handler;

import by.smirnov.common.domain.Message;
import by.smirnov.common.room.Room;

public interface ChatHandler extends Runnable {

    Message handle(Message message);

    void setRoom(Room room);
}
