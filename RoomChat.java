import java.util.List;

public class RoomChat implements IRoomChat {
    private String roomName;
    private List<IUserChat> users;

    @Override
    public void sendMsg(String usrName, String msg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMsg'");
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
