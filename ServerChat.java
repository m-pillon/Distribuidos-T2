import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ServerChat implements IServerChat {

    private Map<String, IRoomChat> roomList;

    public ServerChat(){
        roomList = new HashMap<>();
    }

    @Override
    public ArrayList<String> getRooms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRooms'");
    }

    @Override
    public void createRoom(String roomName) {
        roomList.putIfAbsent(roomName, new RoomChat());
        //IRoomChat existing_room = roomList.putIfAbsent(roomName, new RoomChat());
        //if (existing_room != null){
            //já existe uma room com esse nome
        //}

        throw new UnsupportedOperationException("Unimplemented method 'createRoom'");
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
