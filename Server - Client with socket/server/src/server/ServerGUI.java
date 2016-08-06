package server;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Reference: Intermediate java tutorial, episodes 39-46, Bucky, youtube
 *
 * @author skorkmaz, 2013
 */
public class ServerGUI extends JFrame {

    public static final int SERVER_PORT = 6789;

    private JTextField userText;
    private final JTextArea chatWindow;
    
    public ServerGUI() {
        super("Server");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sendMessage(e.getActionCommand());
                        userText.setText("");
                    }
                });
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300, 150);
        setVisible(true);
    }

    public void startRunning() {
        try {
            ServerLogic.createServer();
            while (true) {
                try {
                    showMessage("Waiting for someone to connect...\n");
                    String hostName = ServerLogic.waitForConnection();
                    showMessage("Now connected to " + hostName);
                    
                    ServerLogic.setupStreams();
                    showMessage("\nStreams are now setup!\n");
                    
                    whileChatting();
                } catch (EOFException e) {
                    showMessage("\nServer ended the connection!");
                } finally {
                    closeCrap();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * During the chat conversations.
     */
    private void whileChatting() throws IOException {
        String message = "You are now connected!";
        sendMessage(message);
        ableToType(true);
        do {
            try {
                message = ServerLogic.getMessageFromClient();
                showMessage("\n" + message);
            } catch (ClassNotFoundException e) {
                showMessage("\nI could not figure out what the client send!");
            }
        } while (!message.equals("CLIENT - END"));
    }

    /**
     * Close streams and sockets after you are done chatting.
     */
    private void closeCrap() {
        showMessage("\nClosing connections...\n");
        ableToType(false);
        ServerLogic.closeCrap();
    }

    /**
     * Send a message to client.
     */
    private void sendMessage(String message) {
        try {
            ServerLogic.sendMessageToClient(message);
            showMessage("\nSERVER - " + message);
        } catch (IOException e) {
            chatWindow.append("\nERROR: DUDE I COULDN'T SEND THAT MESSAGE!");
        }
    }

    /**
     * Updates chatWindow.
     *
     * @param text
     */
    private void showMessage(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatWindow.append(text);
            }
        });
    }

    /**
     * Let the user type stuff into their box.
     *
     * @param b
     */
    private void ableToType(final boolean b) {
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        userText.setEditable(b);
                    }
                });
    }
}
