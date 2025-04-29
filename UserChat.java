import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserChat extends UnicastRemoteObject implements IUserChat {
    protected UserChat() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }

    private String userName;
    private String roomName;
    private IServerChat serverChat;
    
    @Override
    public void deliverMsg(String senderName, String msg) {
        System.out.println("[" + roomName + "] " + senderName + ": " + msg);
    }
    
    public void joinRoom(String roomName){
        try {
            IRoomChat room = (IRoomChat) Naming.lookup(roomName);
            room.joinRoom(userName, this);
        } catch (NotBoundException e) {
            serverChat.createRoom(roomName);
            joinRoom(roomName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}
