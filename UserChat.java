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
    private IRoomChat roomChat;
    private IServerChat serverChat;

    private UserGUI userGUI;

    public UserChat(String userName, String roomName, IServerChat serverChat) throws RemoteException {
        this.userName = userName;
        this.roomName = roomName;
        this.serverChat = serverChat;
        this.userGUI = new UserGUI(this);
        // this.userGUI.setUserName(userName);
        // this.userGUI.setRoomName(roomName);
    }

    @Override
    public void deliverMsg(String senderName, String msg) {
        System.out.println("[" + roomName + "] " + senderName + ": " + msg);
    }

    public IServerChat getServerChat() {
        return serverChat;
    }
    
    public void joinRoom(String newRoomName){
        if (roomChat != null){
            try {
                IRoomChat oldRoom = (IRoomChat) Naming.lookup(this.roomName);
                oldRoom.leaveRoom(userName);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        try {
            IRoomChat room = (IRoomChat) Naming.lookup(newRoomName);
            room.joinRoom(userName, this);
        } catch (NotBoundException e) {
            //sala não existe, cria ela 
            serverChat.createRoom(newRoomName);
            joinRoom(newRoomName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}
