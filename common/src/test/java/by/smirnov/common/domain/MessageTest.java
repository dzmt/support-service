package by.smirnov.common.domain;

import by.smirnov.common.enumeration.Type;
import by.smirnov.common.util.Messages;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {
    @Test
    public void getType() throws Exception {
        assertEquals(Type.CONTENT, Messages.GREETING.getType());
        assertEquals(Type.EXIT, Messages.EXIT.getType());
    }

    @Test
    public void getText() throws Exception {
        assertEquals("", Messages.EXIT.getText());
        assertEquals("Sorry, agent has left the chat. Please, ask a question to other agent.", Messages.AGENT_EXITED.getText());
    }

    @Test
    public void equals() throws Exception {
        assertEquals(new Message(Type.CONTENT, "Hello"), new Message(Type.CONTENT, "Hello"));
        assertEquals(new Message(Type.ERROR, ""), new Message(Type.ERROR, ""));
    }

}