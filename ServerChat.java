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

    @Override
    public void createRoom(String roomName) {
        IRoomChat existing_room = roomList.putIfAbsent(roomName, new RoomChat());
        if (existing_room != null){
            System.out.println("Room already exists.");
        }
    }
    
    public static void main(String[] args) {
        try {
            ServerChat newServer = new ServerChat();
            ServerChat stub = (ServerChat) UnicastRemoteObject.exportObject(newServer, 0);
            Naming.rebind("nomeWIP", stub);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
