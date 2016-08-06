package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * Reference: Intermediate java tutorial, episodes 47-59, Bucky, YouTube
 *
 * @author skorkmaz
 */
public class ClientView extends javax.swing.JFrame {

    public ClientView() {
        initComponents();
        jtfMessage.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sendMessage(e.getActionCommand());
                        jtfMessage.setText("");
                    }
                });
    }

    public void startRunning() {
        try {
            connectToServer();
            setupStreams();
            whileChatting();
        } catch (EOFException e) {
            showMessage("\nClient terminated connection");
        } catch (IOException e) {
            if (e instanceof java.net.SocketException) {
                showMessage("Could not connect to server at IP address " + ClientLogic.getServerIP());
            } else {
                showMessage("Exception occured while trying to connect.");
                System.out.println(e);
            }
        } finally {
            closeCrap();
        }
    }

    /**
     * Connect to server.
     */
    private void connectToServer() throws IOException {
        showMessage("Attempting connection...\n");
        String serverName = ClientLogic.connectToServer();
        showMessage("Connected to: " + serverName);
    }

    /**
     * Set up streams to send and receive messages.
     */
    private void setupStreams() throws IOException {
        ClientLogic.setupStreams();
        showMessage("\nDude your streams are now good to go!\n");
    }

    /**
     * While chatting with server.
     */
    private void whileChatting() throws IOException {
        String message = "";
        ableToType(true);
        do {
            try {
                message = ClientLogic.getMessageFromServer();
                showMessage("\n" + message);
            } catch (ClassNotFoundException e) {
                showMessage("\nI don't know that object type");
            }
        } while (!message.equals("SERVER - END"));
    }

    /**
     * Close the streams and socket.
     */
    private void closeCrap() {
        showMessage("\nClosing crap down...\n");
        ableToType(false);
        try {
            ClientLogic.closeCrap();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Send message to server.
     */
    private void sendMessage(String message) {
        try {
            ClientLogic.sendMessageToServer(message);
            showMessage("\nCLIENT - " + message);
        } catch (IOException e) {
            jtaChat.append("\nSomething messed up sending message!");
        }
    }

    /**
     * Updates chatWindow.
     *
     * @param text
     */
    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        jtaChat.append(text);
                    }
                });
    }

    /**
     * Let the user type stuff into their box.
     *
     * @param b
     */
    private void ableToType(final boolean b) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        jtfMessage.setEditable(b);
                    }
                });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the
     * Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfServerIP = new javax.swing.JTextField();
        jbConnetToServer = new javax.swing.JButton();
        jtfMessage = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaChat = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Server IP:");

        jtfServerIP.setText("169.254.19.163");

        jbConnetToServer.setText("Connect");
        jbConnetToServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConnetToServerActionPerformed(evt);
            }
        });

        jtfMessage.setEditable(false);

        jtaChat.setColumns(20);
        jtaChat.setRows(5);
        jScrollPane1.setViewportView(jtaChat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfMessage)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbConnetToServer))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbConnetToServer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbConnetToServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConnetToServerActionPerformed
        ClientLogic.setServerIP(jtfServerIP.getText());
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                startRunning();
                return null;
            }

            @Override
            protected void done() {
                //
            }
        }.execute();

    }//GEN-LAST:event_jbConnetToServerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbConnetToServer;
    private javax.swing.JTextArea jtaChat;
    private javax.swing.JTextField jtfMessage;
    private javax.swing.JTextField jtfServerIP;
    // End of variables declaration//GEN-END:variables
}