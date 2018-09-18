package by.smirnov.common.connection.impl;

import by.smirnov.common.connection.Connection;
import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;
import by.smirnov.common.util.Messages;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SocketConnectionTest {
    @Test
    public void sendReceive() throws Exception {
        try (FileOutputStream fout = new FileOutputStream(new File("socketConnectionTestSend"));
             FileInputStream fin = new FileInputStream(new File("socketConnectionTestSend"))) {

            Socket socket = mock(Socket.class);

            when(socket.getOutputStream()).thenReturn(fout);
            when(socket.getInputStream()).thenReturn(fin);

            Connection connection = new SocketConnection(socket);

            connection.send(Messages.OK_EXIT);
            Message message = connection.receive();
            assertEquals(message, Messages.OK_EXIT);

            message = new Message(Type.CONTENT, "Hello");
            connection.send(message);
            assertEquals(message.toString(), connection.receive(), new Message(Type.CONTENT, "Hello"));

            Message error = new Message(Type.CONTENT, "error");
            connection.send(error);
            assertNotEquals(new Message(Type.CONTENT, "erro"), connection.receive());

        } catch (EOFException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isAvailable() throws Exception {
        try (FileOutputStream fout = new FileOutputStream(new File("socketConnectionTestAvailable"));
             FileInputStream fin = new FileInputStream(new File("socketConnectionTestAvailable"))) {

            Socket socket = mock(Socket.class);

            when(socket.getOutputStream()).thenReturn(fout);
            when(socket.getInputStream()).thenReturn(fin);

            Connection connection = new SocketConnection(socket);
            assertFalse(connection.isAvailable());

            connection.send(Messages.GREETING);
            assertTrue(connection.isAvailable());

        }
    }

}