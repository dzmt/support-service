package by.smirnov.agent;

import by.smirnov.common.enumeration.UserType;

import java.io.IOException;

public class AgentLauncher {
    public static void main(String[] args) throws IOException {
        Agent agent = new Agent(UserType.AGENT, "188.225.56.206", 9999);
        try {
            agent.start();
        } catch (IOException e) {
            System.out.println("Sorry. Could not connect to the server. Please, try later.");
        }
    }
}
