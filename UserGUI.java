import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

public class UserGUI {
    public JFrame frame;
    public JTextArea textArea;
    private String userName;
    private String roomName;
    public JButton sendButton;
    public JButton leaveButton;
    public JButton joinButton;
    public JButton createButton;
    private UserChat user;

    public UserGUI(UserChat user) {
        this.user = user;
        this.frame = new JFrame("User Chat");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);

        sendButton = new JButton("Send Message");
        sendButton.addActionListener(e -> sendMessage());

        leaveButton = new JButton("Leave Room");
        leaveButton.addActionListener(e -> leaveRoom());

        joinButton = new JButton("Join Room");
        joinButton.addActionListener(e -> joinRoom());

        createButton = new JButton("Create Room");
        createButton.addActionListener(e -> createRoom());

        setupUserGUI();
    }

    public String setUserName() {
        String name = javax.swing.JOptionPane.showInputDialog(frame, "Enter your name:", "User Name", javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            this.userName = name;
            frame.setTitle("Chat Room - " + userName);
            return userName;
        } else {
            return null;
        }
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    private void createRoom() {
        String roomName = javax.swing.JOptionPane.showInputDialog(frame, "Enter room name:", "New Room", javax.swing.JOptionPane.PLAIN_MESSAGE);
        
        this.user.createRoom(roomName);
    }

    private void setupUserGUI() {
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        frame.add(textArea);

        frame.add(createButton, "North");

        // get available rooms from server - RFA5
        try {
            ArrayList<String> availableRooms = user.getServerChat().getRooms();
            StringBuilder rooms = new StringBuilder("Available Rooms:\n");
            for (String room : availableRooms) {
                rooms.append(room).append("\n");

                // show join button next to each room
                JButton joinRoomButton = new JButton("Join " + room);
                joinRoomButton.addActionListener(e -> {
                    user.joinRoom(room);
                });
                frame.add(joinRoomButton);
            }

            textArea.setText(rooms.toString());
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        frame.setVisible(true);
    }

    public void addWindowListener(WindowListener exitListener) {
        frame.addWindowListener(exitListener);
    }

    // TODO add action listeners to buttons
    // TODO add text field for user input

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
