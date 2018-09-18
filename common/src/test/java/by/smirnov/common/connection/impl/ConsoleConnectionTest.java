package by.smirnov.common.connection.impl;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;
import by.smirnov.common.util.Messages;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ConsoleConnectionTest {

    @Test
    public void send() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArrayOutputStream);
        BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("hello".getBytes())));

        ConsoleConnection console = new ConsoleConnection(out, in);

        console.send(Messages.AGENT_EXITED);
        assertEquals(Messages.AGENT_EXITED.getText()+"\r\n", byteArrayOutputStream.toString());
        byteArrayOutputStream.reset();

        console.send(Messages.LEAVE);
        assertEquals(Messages.LEAVE + "\r\n", byteArrayOutputStream.toString());
        byteArrayOutputStream.reset();

        console.send(Messages.LEAVE);
        assertNotEquals(Messages.LEAVE, byteArrayOutputStream.toString());
        byteArrayOutputStream.reset();


    }

    @Test
    public void receive() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("hello\n/exit\n/leave".getBytes());
        PrintStream out = new PrintStream(byteArrayOutputStream);
        BufferedReader in = new BufferedReader(new InputStreamReader(byteArrayInputStream));

        ConsoleConnection console = new ConsoleConnection(out, in);
        Message message = console.receive();
        assertEquals(new Message(Type.CONTENT, "hello"), message);

        message = console.receive();
        assertEquals(Messages.EXIT, message);

        message = console.receive();
        assertEquals(Messages.LEAVE, message);
    }

    @Test
    public void isAvailable() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("hello\n/exit\n/leave".getBytes());
        PrintStream out = new PrintStream(byteArrayOutputStream);
        BufferedReader in = new BufferedReader(new InputStreamReader(byteArrayInputStream));

        ConsoleConnection console = new ConsoleConnection(out, in);
        assertTrue(console.isAvailable());
    }
}