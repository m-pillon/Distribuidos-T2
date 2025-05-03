import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class UserChat extends UnicastRemoteObject implements IUserChat {
    private String userName;
    private String roomName;
    private IRoomChat roomChat;
    private IServerChat serverChat;

    private UserGUI userGUI;

    public UserChat(IServerChat serverChat) throws RemoteException {
        this.serverChat = serverChat;
        this.userGUI = new UserGUI(this);

        // add windowlistener to the GUI
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                leaveRoom();
            }
        };
        userGUI.addWindowListener(exitListener);
        
        this.userName = this.userGUI.setUserName();
        // this.userGUI.setRoomName(roomName);
    }

    public void leaveRoom() {
        if (roomChat == null) {
            return;
        }

        roomChat.leaveRoom(userName);
        userName = "";
        roomChat = null;

        userGUI.leaveButton.setEnabled(false);
        userGUI.textArea.setEditable(false);
        userGUI.textArea.setText("");
        userGUI.frame.setTitle("Join some room to choose a nickname");
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
        } catch (NotBoundException notBound) {
            //sala não existe, cria ela             
            try {
                serverChat.createRoom(newRoomName);
                joinRoom(newRoomName);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    public void createRoom(String roomName){
        try {
            serverChat.createRoom(roomName);
            // joinRoom(roomName);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        int port = 2020; // RFA15

        try {
            System.out.println("Trying to connect to server on port " + port);
            Registry Servidor = LocateRegistry.getRegistry("localhost", port);
            IServerChat server = (IServerChat) Servidor.lookup("Servidor");
            new UserChat(server);
            System.out.println("Connected to server on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
