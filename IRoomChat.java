import java.util.ArrayList;

public interface IRoomChat extends java.rmi.Remote {
    public void sendMsg(String usrName, String msg) throws java.rmi.RemoteException;
    public void joinRoom(String userName, IUserChat user) throws java.rmi.RemoteException;
    public void leaveRoom(String usrName) throws java.rmi.RemoteException;
    public String getRoomName() throws java.rmi.RemoteException;
    public void closeRoom() throws java.rmi.RemoteException;
    public ArrayList<UserChat> getUsers() throws java.rmi.RemoteException;
}
