package by.smirnov.common.connection;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Status;

import java.io.IOException;

public interface Connection {
    void send(Message message) throws IOException;

    Message receive() throws IOException;

    boolean isAvailable() throws IOException;

    void close() throws IOException;

    Status getStatus();

    void setStatus(Status status);
}
