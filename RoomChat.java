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
        //TODO Auto-generated constructor stub
    }

    private String roomName;
    private Map<String, IUserChat> userList;

    @Override
    public void sendMsg(String usrName, String msg) {
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
    public void joinRoom(String userName, IUserChat user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinRoom'");
    }

    @Override
    public void leaveRoom(String usrName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'leaveRoom'");
    }

    @Override
    public String getRoomName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoomName'");
    }

    @Override
    public void closeRoom() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeRoom'");
    }
    
}
