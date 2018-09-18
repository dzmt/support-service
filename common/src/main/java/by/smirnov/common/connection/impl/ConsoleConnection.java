package by.smirnov.common.connection.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Status;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.util.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ConsoleConnection implements Connection {
    private PrintStream out;
    private BufferedReader in;
    private Status status = Status.ACTIVE;

    public ConsoleConnection(PrintStream out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    @Override
    public void send(Message message) throws IOException {
        out.println(message.getText());
    }

    @Override
    public Message receive() throws IOException {
        String text = in.readLine();
        Message message = Parser.parse(text);
        return message;
    }

    @Override
    public boolean isAvailable() throws IOException {
        return in.ready();
    }

    @Override
    public void close() throws IOException {
        out.close();
        in.close();
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
