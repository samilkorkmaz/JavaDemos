package server;

import javax.swing.JFrame;

/**
 *
 * @author skorkmaz, 2013
 */
public class ServerTest {
    
    public static void main(String[] args) {
        ServerGUI server = new ServerGUI();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.startRunning();
    }
    
}
