package by.smirnov.server.service;

import by.smirnov.common.room.Room;

public interface RoomController extends Runnable {

    Room createRoom();

    void handleClosedRoom();
}
