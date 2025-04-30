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
    private UserChat user;

    public UserGUI(UserChat user) {
        this.user = user;
        this.frame = new JFrame("User Chat");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);

        setupUserGUI();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    private void createRoom() {
        // TODO
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
        // TODO
    }

    private void leaveRoom() {
        // TODO
    }

    private void joinRoom() {
        // TODO
    }
}
