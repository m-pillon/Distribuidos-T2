import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
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
        joinButton.addActionListener(e -> {
            try {
                joinRoom();
            } catch (RemoteException e1) {
                System.out.println("Error joining room: " + e1.getMessage());
            }
        });

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
        String roomName = javax.swing.JOptionPane.showInputDialog(frame, "Enter room name:", "Create Room", javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (roomName != null && !roomName.trim().isEmpty()) {
            try {
                user.getServerChat().createRoom(roomName);
                updateRoomList(roomName);
                setRoomName(roomName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(frame, "Room name cannot be empty.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateRoomList(String roomName) {
        StringBuilder rooms = new StringBuilder("Available Rooms:\n");
        try {
            ArrayList<String> availableRooms = user.getServerChat().getRooms();
            for (String room : availableRooms) {
                rooms.append(room).append("\n");
            }
            textArea.setText(rooms.toString());
            
            frame.add(textArea, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateTextArea(String message) {
        textArea.append(message + "\n");
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
           // print available rooms
            for (String room : availableRooms) {
                rooms.append(room).append("\n");
            }
            // set text area to available rooms
            textArea.setText(rooms.toString());
            frame.add(textArea, "Center");
        } catch (Exception e) {
            System.out.println("Error getting available rooms: " + e.getMessage());
        }

        frame.add(joinButton, "South");
        
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

    private void setupChatGUI() {
        textArea.setEditable(true);
        frame.setTitle("Chat Room - " + userName + " - " + roomName);
        frame.add(sendButton, BorderLayout.SOUTH);
        frame.add(leaveButton, BorderLayout.EAST);
    }

    private void joinRoom() throws RemoteException {
        String roomName = javax.swing.JOptionPane.showInputDialog(frame, "Enter room name:", "Join Room", javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (roomName != null && !roomName.trim().isEmpty()) {
            Boolean joined = user.joinRoom(roomName);
            if (joined) {
                setRoomName(roomName);
                setupChatGUI();
            } else {
                javax.swing.JOptionPane.showMessageDialog(frame, "Room not found. Try creating it!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(frame, "Room name cannot be empty.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
