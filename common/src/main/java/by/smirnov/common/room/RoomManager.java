package by.smirnov.common.room;

import by.smirnov.common.connection.Connection;
import by.smirnov.common.enumeration.UserType;
import by.smirnov.common.room.impl.AgentRoom;
import by.smirnov.common.room.impl.ClientRoom;

public class RoomManager {

    public static Room getRoom(Connection console, Connection server, UserType type) {
        Room room = null;
        if (type.equals(UserType.CLIENT)){
            room = new  ClientRoom(console, server);
        } else if (type.equals(UserType.AGENT)){
            room = new AgentRoom(console, server);
        }

        return room;
    }
}
