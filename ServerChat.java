import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class ServerChat extends UnicastRemoteObject implements IServerChat {
    protected ServerChat() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }

    private Map<String, IRoomChat> roomList;

    public Map<String, IRoomChat> getRooms() {
        return roomList;
    }

    public void createRoom(String roomName) throws RemoteException {
        if (!roomList.containsKey(roomName)) {
            roomList.put(roomName, new RoomChat(roomName));
        } else {
            System.out.println("Room already exists.");
        }
    }
    
}
