import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class UserGUI {
    public JFrame frame;
    private JTextArea textArea;
    private String userName;
    private String roomName;
    private JButton sendButton;
    private JButton leaveButton;
    private JButton joinButton;
    private JButton createButton;

    private String selectedRoom;

    private UserChat user;
    private RoomChat room;

    public UserGUI(UserChat user, ServerChat server) {
        this.user = user;
        this.frame = new JFrame("User Chat");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);

        setupUserGUI();
    }

    private void createRoom() {
        String roomName = javax.swing.JOptionPane.showInputDialog(frame, "Enter room name:");
        if (roomName != null && !roomName.isEmpty()) {
            user.getServerChat().createRoom(roomName);
        }
    }

    private void setupUserGUI() {
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        frame.add(textArea);

        sendButton = new JButton("Send Message");
        sendButton.addActionListener(e -> sendMessage());

        leaveButton = new JButton("Leave Room");
        leaveButton.addActionListener(e -> leaveRoom());

        joinButton = new JButton("Join Room");
        joinButton.addActionListener(e -> joinRoom());

        createButton = new JButton("Create Room");
        createButton.addActionListener(e -> createRoom());

        frame.add(sendButton);
        frame.add(leaveButton);
        frame.add(joinButton);
        frame.add(createButton);

        frame.setVisible(true);
    }

    private void sendMessage() {
        String message = textArea.getText();
        if (!message.isEmpty()) {
            user.deliverMsg(userName, message);
            textArea.setText("");
        }
    }

    private void leaveRoom() {
        if (room != null) {
            room.leaveRoom(userName);
            room = null;
        }
    }

    private void joinRoom() {
        ArrayList<String> rooms = user.getServerChat().getRooms();
        javax.swing.JOptionPane.showMessageDialog(frame, "Available rooms: " + rooms.toString());
        String roomName = javax.swing.JOptionPane.showInputDialog(frame, "Enter room name:");
        if (roomName != null && !roomName.isEmpty()) {
            user.joinRoom(roomName);
            this.roomName = roomName;
        }
    }
}
