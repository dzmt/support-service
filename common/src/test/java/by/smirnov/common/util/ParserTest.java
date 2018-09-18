package by.smirnov.common.util;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;
import by.smirnov.common.enumeration.UserType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void parse() throws Exception {
        assertNotNull("Empty string is not null", Parser.parse(""));
        assertEquals(new Message(Type.CONTENT, "hello"), Parser.parse("hello"));
        assertEquals(Messages.EXIT, Parser.parse("/exit"));
        assertEquals(Messages.EXIT, Parser.parse(" /exit from the system"));
        assertEquals(Messages.EXIT, Parser.parse("    /ExiT from the system"));
        assertNotEquals(Messages.EXIT, Parser.parse("    ExiT from the system"));
        assertEquals(Messages.LEAVE, Parser.parse("    /leave chat"));
        assertEquals(Messages.LEAVE, Parser.parse("/leave"));
    }

    @Test
    public void getRegisterMessage() throws Exception {
        assertNotNull(Parser.getRegisterMessage(UserType.CLIENT, "Dima"));
        assertEquals(new Message(Type.REGISTER, "CLIENT Dzmitry"), Parser.getRegisterMessage(UserType.CLIENT, "Dzmitry"));
        assertEquals(new Message(Type.REGISTER, "AGENT Dzmitry"), Parser.getRegisterMessage(UserType.AGENT, "Dzmitry"));
        assertNotEquals(new Message(Type.REGISTER, "AGENT Dzmitry"), Parser.getRegisterMessage(UserType.AGENT, "Olga"));
    }

    @Test
    public void getGreetingMessage() throws Exception {
        String greetingClient = "Hello, Dzmitry. You are registered in the system. Need You help? Ask a question and our agents will answer soon.";
        String greetingAgent = "Hello, Dzmitry. You are registered in the system. Please, wait a question from clients.";


        assertEquals(greetingClient, Parser.getGreetingMessage("Dzmitry", "CLIENT").getText());
        assertEquals(greetingAgent, Parser.getGreetingMessage("Dzmitry", "AGENT").getText());
    }

}