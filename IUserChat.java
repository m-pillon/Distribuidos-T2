import java.rmi.RemoteException;

public interface IUserChat extends java.rmi.Remote {
    public void deliverMsg(String senderName, String msg) throws RemoteException;
    public String getUserName() throws RemoteException;
    public void leaveRoom() throws RemoteException;
    public UserGUI getUserGUI() throws RemoteException;
}
    