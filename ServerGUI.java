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

    Scanner in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JScrollPane scrollPane;
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> chatList;
    ServerChat serverChat;

    public ServerGUI(ServerChat server) {
        this.serverChat = server;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);

        setupServerGUI();
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
    }

    private void closeSelectedRoom() {
        // TODO
    }

    private void updateListModel() {
        // TODO
    }
}