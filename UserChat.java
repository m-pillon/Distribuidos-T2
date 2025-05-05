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
    private Registry registry;

    private UserGUI userGUI;

    public UserChat(IServerChat serverChat, Registry registry) throws RemoteException {
        this.serverChat = serverChat;
        this.userGUI = new UserGUI(this);
        this.registry = registry;

        // add windowlistener to the GUI
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    leaveRoom();
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        };
        userGUI.addWindowListener(exitListener);
        
        this.userName = this.userGUI.setUserName();
        // this.userGUI.setRoomName(roomName);
    }

    public void leaveRoom() throws RemoteException {
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
        //mensagem do controlador
        if (senderName == null) {
            //sinal que sala foi fechada
            if (msg.equalsIgnoreCase("Sala fechada pelo servidor.")) {
                roomChat = null;
                //TODO: avisar pro usuário no GUI que a sala foi fechada
                userGUI.getMessage("Servidor", msg);
                return;
            }
        }
        userGUI.getMessage(senderName, msg);
    }

    public IServerChat getServerChat() {
        return serverChat;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public IRoomChat getRoomChat() {
        return roomChat;
    }

    public String getUserName() {
        return userName;
    }
    
    public Boolean joinRoom(String newRoomName){
        if (roomChat != null){
            try {
                IRoomChat oldRoom = (IRoomChat) registry.lookup(this.roomName);
                oldRoom.leaveRoom(userName);
            } catch (Exception e) {
                System.out.println("Error leaving old room: " + e.getMessage());
            }
        }

        try {
            
            IRoomChat room = (IRoomChat) registry.lookup(newRoomName);
            //IRoomChat room = (IRoomChat) Naming.lookup(newRoomName);
            room.joinRoom(userName, this);
            this.roomChat = room;
            return true;
        } catch (Exception e) {
            //sala não existe
            System.out.println("Error joining room: " + e.getMessage());            
            return false;
        } 
    }

    public static void main(String[] args) {
        int port = 2020; // RFA15

        try {
            System.out.println("Trying to connect to server on port " + port);
            Registry Servidor = LocateRegistry.getRegistry("localhost", port);
            IServerChat server = (IServerChat) Servidor.lookup("Servidor");
            new UserChat(server, Servidor);
            System.out.println("Connected to server on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getRegistry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRegistry'");
    }
}
