package by.smirnov.server;

import by.smirnov.common.domain.Message;
import by.smirnov.common.enumeration.UserType;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.connection.impl.SocketConnection;
import by.smirnov.common.util.Parser;
import by.smirnov.server.service.AgentBase;
import by.smirnov.server.service.RoomController;
import by.smirnov.server.service.WaitingRoom;
import by.smirnov.server.service.impl.AgentBaseImpl;
import by.smirnov.server.service.impl.RoomControllerImpl;
import by.smirnov.server.service.impl.WaitingRoomImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);

    private int port = 9999;
    private ServerSocket serverSocket;
    private WaitingRoom waitingRoom = new WaitingRoomImpl();
    private AgentBase agents = new AgentBaseImpl();
    private RoomController roomController = new RoomControllerImpl(waitingRoom, agents);

    public void start() throws IOException {
        logger.info("Server is launched.");
        startListen();
        startLoop();
        register();
    }

    private void startListen() throws IOException{
        serverSocket = new ServerSocket(port);
        logger.info("Server has started listening port: " + port);
    }

    private void register() throws IOException{
        while (true) {
            Socket socket = serverSocket.accept();
            Connection connection = new SocketConnection(socket);

            Message registerMessage = connection.receive();

            String[] messages = registerMessage.getText().split(" ");
            switch (UserType.valueOf(messages[0])){
                case CLIENT: {
                    waitingRoom.addClient(connection);
                }
                break;
                case AGENT: {
                    agents.addAgent(connection);
                }
                break;
            }
            connection.send(Parser.getGreetingMessage(messages[1], messages[0]));
            logger.info(registerMessage.getText() + ": is registered in the system.");
        }
    }

    private void startLoop(){
        new Thread(roomController, "RoomController").start();
    }
}
