package by.smirnov.common.util;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.Type;
import by.smirnov.common.enumeration.UserType;

import java.util.HashMap;

public class Parser {

    private static HashMap<String, Message> commands = new HashMap<>();

    static {
        commands.put("exit", new Message(Type.EXIT, ""));
        commands.put("leave", new Message(Type.LEAVE, ""));
    }

    public static Message parse(String text) {
        if (text.trim().startsWith("/")) {
            String command = text.trim().toLowerCase().split(" ")[0].substring(1);
            if (commands.containsKey(command)) {
                return commands.get(command);
            }
        }
        return new Message(Type.CONTENT, text);
    }

    public static Message getRegisterMessage(UserType type, String name) {
        StringBuilder registerMessage = new StringBuilder();
        registerMessage
                .append(type.name())
                .append(" ")
                .append(name);
        return new Message(Type.REGISTER, registerMessage.toString());
    }


    public static Message getGreetingMessage(String name, String type) {
        String greeting = "Hello, " + name + ". You are registered in the system.";
        if (type.equals("CLIENT")) {
            greeting += " Need You help? Ask a question and our agents will answer soon.";
        } else if (type.equals("AGENT")) {
            greeting += " Please, wait a question from clients.";
        }
        return new Message(Type.CONTENT, greeting);
    }

}
