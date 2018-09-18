package by.smirnov.server.service.impl;

import by.smirnov.common.enumeration.Status;
import by.smirnov.common.connection.Connection;
import by.smirnov.common.room.Room;
import by.smirnov.common.room.impl.ServerRoom;
import by.smirnov.server.service.AgentBase;
import by.smirnov.server.service.RoomController;
import by.smirnov.server.service.WaitingRoom;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class RoomControllerImpl implements RoomController {
    private static final Logger logger = Logger.getLogger(RoomControllerImpl.class);

    private WaitingRoom waitingRoom;
    private AgentBase agentBase;

    private ConcurrentLinkedQueue<Connection> clientsQueue = new ConcurrentLinkedQueue<>();
    private ArrayList<Room> rooms = new ArrayList<>();

    public RoomControllerImpl(WaitingRoom waitingRoom, AgentBase agentBase) {
        this.waitingRoom = waitingRoom;
        this.agentBase = agentBase;
    }

    @Override
    public Room createRoom() {
        if (agentBase.hasAgents() && clientsQueue.size() > 0) {
            logger.info("Pair is found.");
            try {
                Room room = new ServerRoom(clientsQueue.poll(), agentBase.getVacantAgent());
                room.start();
                rooms.add(room);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void handleClosedRoom() {
        List<Room> closedRooms = rooms
                .stream()
                .filter(room -> !room.isOpen())
                .collect(Collectors.toList());


        closedRooms.forEach(room -> {
            Connection[] connections = room.getConnections();
            Connection client = connections[0];
            if (client.getStatus().equals(Status.ACTIVE)) {
                waitingRoom.addClient(connections[0]);
            }
            Connection agent = connections[1];
            if (agent.getStatus().equals(Status.ACTIVE)) {
                agentBase.addAgent(connections[1]);
            }
        });

        rooms.removeAll(closedRooms);

    }

    @Override
    public void run() {
        logger.info("The loop for checking client and agent queues is started");
        while (true) {
            checkConnections();

            createRoom();

            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            handleClosedRoom();
        }

    }

    private void checkConnections() {
        List<Connection> clients = waitingRoom.findReadyClients();
        if (clients.size() > 0) {
            clientsQueue.addAll(clients);
            logger.info("Find ready to talk clients: " + clients.size() + ". Agent base has free agent: " + agentBase.hasAgents());
        }

        agentBase.checkReadyState();
    }
}
