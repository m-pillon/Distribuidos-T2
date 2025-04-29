public class UserChat implements IUserChat {
    private String userName;
    private String roomName;
    private IServerChat serverChat;
    
    public UserChat(String userName, String roomName, IServerChat serverChat) {
        this.userName = userName;
        this.roomName = roomName;
        this.serverChat = serverChat;
    }
    
    @Override
    public void deliverMsg(String senderName, String msg) {
        System.out.println("[" + roomName + "] " + senderName + ": " + msg);
    }
}
