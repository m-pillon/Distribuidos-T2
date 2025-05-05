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
        // receivers.remove(usrName);

        for (IUserChat user : receivers.values()) {
            try {
                user.deliverMsg(usrName, msg);
            } catch (Exception e) {
                System.out.println("Erro ao enviar mensagem para " + user.getUserName() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void joinRoom(String userName, IUserChat user) throws RemoteException {
        userList.put(userName, user);
    }

    @Override
    public void leaveRoom(String usrName) throws RemoteException {
        userList.remove(usrName);
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

    @Override
    public void closeRoom() throws RemoteException {
        for (IUserChat user : userList.values()) {
            try {
                user.deliverMsg(null, "Sala fechada pelo servidor.");
                user.leaveRoom();
            } catch (Exception e) {
                System.out.println("Erro ao enviar mensagem de fechamento para " + user.getUserName() + ": " + e.getMessage());
            }
        }
    }
    
}
