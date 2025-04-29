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
}
