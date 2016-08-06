package client;

/**
 *
 * @author skorkmaz, 2013.
 */
public class ClientTest {

    public static void main(String[] args) { 
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientView client = new ClientView();
                client.setVisible(true);
            }
        });
    }
}
