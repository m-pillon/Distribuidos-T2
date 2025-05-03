import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomChat extends UnicastRemoteObject implements IRoomChat {
    protected RoomChat(String roomName) throws RemoteException {
        this.roomName = roomName;
        this.userList = new java.util.HashMap<String, IUserChat>();
    }

    private String roomName;
    private Map<String, IUserChat> userList;

    @Override
    public void sendMsg(String usrName, String msg) throws RemoteException {
        Map<String, IUserChat> receivers = new HashMap<String, IUserChat>(userList);
        receivers.remove(usrName);

        for (IUserChat user : receivers.values()) {
            try {
                user.deliverMsg(usrName, msg);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    @Override
    public void joinRoom(String userName, IUserChat user) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinRoom'");
    }

    @Override
    public void leaveRoom(String usrName) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'leaveRoom'");
    }

    @Override
    public String getRoomName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoomName'");
    }

    @Override
    public void closeRoom() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeRoom'");
    }
    
}
