package by.smirnov.common.connection.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Status;
import by.smirnov.common.connection.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnection implements Connection {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private Status status;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        status = Status.ACTIVE;
    }

    @Override
    public void send(Message message) throws IOException {
        out.writeObject(message);
    }

    @Override
    public Message receive() throws IOException {
        Message message = null;
        try {
            message = (Message) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean isAvailable() throws IOException {
        return socket.getInputStream().available() > 0;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
