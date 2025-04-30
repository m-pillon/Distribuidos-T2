import java.io.PrintWriter;
import java.util.Scanner;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


public class ServerGUI {

    ServerChat server;
    Scanner in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JScrollPane scrollPane;
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> chatList;

    public ServerGUI(ServerChat server) {
        this.server = server;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);

        setupServerGUI();
    }

    private void setupServerGUI() {
        JLabel label = new JLabel("Number of rooms active: " + server.getNumberOfRooms());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);

        frame.getContentPane().add(label, BorderLayout.NORTH);

        // rooms
        updateListModel();

        chatList = new JList<>(listModel);
        chatList.setLayoutOrientation(JList.VERTICAL);
        chatList.setVisibleRowCount(-1);

        scrollPane = new JScrollPane(chatList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton closeRoomButton = new JButton("Close Room");
        closeRoomButton.addActionListener(e -> closeSelectedRoom());

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(closeRoomButton, BorderLayout.SOUTH);
    }

    private void closeSelectedRoom() {
        int selectedIndex = chatList.getSelectedIndex();
        if (selectedIndex != -1) {
            server.removeRoom((String) chatList.getSelectedValue());
            updateListModel();
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a chat room to close.");
        }
    }

    private void updateListModel() {
        listModel.clear();
        for (String room : server.getRooms()) {
            listModel.addElement(room);
        }
    }
}