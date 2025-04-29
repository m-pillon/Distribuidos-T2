import java.util.ArrayList;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ServerChat implements IServerChat {


    @Override
    public ArrayList<String> getRooms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRooms'");
    }

    @Override
    public void createRoom(String roomName) {
        
        // TODO Auto-generated method stub
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
