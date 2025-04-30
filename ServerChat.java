import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.*;
import java.util.Map;

public class ServerChat extends UnicastRemoteObject implements IServerChat {
    protected ServerChat() throws RemoteException {
        super();
        this.roomList = new HashMap<String, IRoomChat>();
    }

    private Map<String, IRoomChat> roomList;

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
            ServerChat stub = (ServerChat) UnicastRemoteObject.exportObject(newServer, 2020);
            Naming.rebind("servidor", stub);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
