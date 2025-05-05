import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ServerGUI {

    Scanner in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JScrollPane scrollPane;
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> chatList;
    ServerChat serverChat;
    JTextArea textArea = new JTextArea(20, 50);
    JLabel label = new JLabel("Available Rooms:");

    public ServerGUI(ServerChat server) {
        this.serverChat = server;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);

        setupServerGUI();
    }

    public void updateRoomList() {
        StringBuilder rooms = new StringBuilder("Available Rooms:\n");
        ArrayList<String> availableRooms = serverChat.getRooms();
        for (String room : availableRooms) {
            rooms.append(room).append("\n");
        }
        textArea.setText(rooms.toString());
        frame.add(textArea, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void setupServerGUI() {
        // rooms
        // updateListModel();

        chatList = new JList<>(listModel);
        chatList.setLayoutOrientation(JList.VERTICAL);
        chatList.setVisibleRowCount(-1);

        scrollPane = new JScrollPane(chatList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton closeRoomButton = new JButton("Close Room");
        closeRoomButton.addActionListener(e -> closeSelectedRoom());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(closeRoomButton, BorderLayout.SOUTH);

        frame.add(label, BorderLayout.NORTH);
        frame.add(textArea, BorderLayout.EAST); 
    }

    private void closeSelectedRoom() {
        String roomName = javax.swing.JOptionPane.showInputDialog(frame, "Enter room name:", "Close Room", javax.swing.JOptionPane.PLAIN_MESSAGE);
        if (roomName != null && !roomName.trim().isEmpty()) {
            try {
                serverChat.getRoom(roomName).closeRoom();
                listModel.removeElement(roomName);
                updateRoomList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(frame, "Room name cannot be empty.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}