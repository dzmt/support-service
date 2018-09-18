package by.smirnov.common.util;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;

public interface Messages {
    Message EXIT = new Message(Type.EXIT, "");
    Message LEAVE = new Message(Type.LEAVE, "");
    Message OK_EXIT = new Message(Type.OK_EXIT, "");
    Message OK_LEAVE = new Message(Type.OK_LEAVE, "");
    Message ERROR_AGENT = new Message(Type.ERROR_AGENT, "");
    Message ERROR_CLIENT = new Message(Type.ERROR_CLIENT, "");
    Message ERROR_SERVER = new Message(Type.ERROR_SERVER, "Server is not available. Press enter key to exit from app.");

    Message ACCEPT_NAME = new Message(Type.CONTENT, "Enter your name, please:");
    Message GREETING = new Message(Type.CONTENT, "Hello. You are registered in the system. Waiting a connection.");
    Message CLIENT_LEAVE = new Message(Type.CONTENT, "Client has left chat. Please, wait other Client.");
    Message ASK_QUESTION = new Message(Type.CONTENT, "Need You help? Ask a question to our agent.");
    Message AGENT_EXITED = new Message(Type.CONTENT, "Sorry, agent has left the chat. Please, ask a question to other agent.");

    Message NOT_SUPPORT = new Message(Type.CONTENT, "Sorry, but you can't leave chat.");

    Message GOODBYE = new Message(Type.CONTENT, "Goodbye.");

    Message ERROR = new Message(Type.ERROR, "");
}
