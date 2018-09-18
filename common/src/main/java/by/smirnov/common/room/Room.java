package by.smirnov.common.room;

import by.smirnov.common.connection.Connection;

public interface Room {

   void start();
   boolean isOpen();
   void close();

   Connection[] getConnections();
}
