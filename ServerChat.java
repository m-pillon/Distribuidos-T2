import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.*;
import java.util.Map;

public class ServerChat extends UnicastRemoteObject implements IServerChat {
    private Map<String, IRoomChat> roomList;
    private ServerGUI serverGUI;

    private static Registry Servidor;

    public ServerChat() throws RemoteException {
        setupRegistry(); // RFA15
        this.serverGUI = new ServerGUI(this);
        this.roomList = new HashMap<String, IRoomChat>();
    }

    private static Registry setupRegistry() {
        if (Servidor == null) {
            try {
                // Create a registry on port 2020
                Servidor = LocateRegistry.createRegistry(2020);
            } catch (RemoteException e1) {
                try {
                    // If the registry already exists, get it
                    Servidor = LocateRegistry.getRegistry("localhost", 2020);
                } catch (RemoteException e2) {
                    // Handle the exception
                    System.out.println("Unexpected issue!! " + e2.getMessage());
                }
            }
        }
        return Servidor;
    }

    public ArrayList<String> getRooms() {
        return new ArrayList<>(roomList.keySet());
    }

    public Integer getNumberOfRooms() {
        return roomList.size();
    }

    public void removeRoom(String roomName) {
        try {
            IRoomChat room = roomList.remove(roomName);
            if (room != null) {
                room.closeRoom();
            } else {
                System.out.println("Room not found.");
            }
        } catch (Exception e) { 
            System.out.println("Error removing room: " + e.getMessage());
        }
    }

    @Override
    public void createRoom(String roomName) {
        try {
            IRoomChat existing_room = roomList.putIfAbsent(roomName, new RoomChat(roomName));
            if (existing_room != null){
                System.out.println("Room already exists.");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public static void main(String[] args) {
        try {
            ServerChat newServer = new ServerChat();
            // RFA15 - Change name to "Servidor"
            setupRegistry().rebind("Servidor", newServer);
            System.out.println("Server started. Waiting for clients...");
            newServer.serverGUI.frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
