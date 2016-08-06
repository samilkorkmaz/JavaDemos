package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author skorkmaz
 */
public class ClientLogic {

    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private static String serverIP;
    private static Socket connection;
    private static final int SERVER_PORT = 6789;

    private ClientLogic() {
    }
    
    public static String getServerIP() {
        return serverIP;
    }

    public static void setServerIP(final String inServerIP) {
        serverIP = inServerIP;
    }

    public static String connectToServer() throws UnknownHostException, IOException {
        connection = new Socket(InetAddress.getByName(serverIP), SERVER_PORT);
        return connection.getInetAddress().getHostName();
    }

    /**
     * Set up streams to send and receive messages.
     */
    public static void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    public static String getMessageFromServer() throws IOException, ClassNotFoundException {
        return (String) input.readObject();
    }

    /**
     * Send message to server.
     */
    public static void sendMessageToServer(String message) throws IOException {
        output.writeObject("CLIENT - " + message);
        output.flush();
    }

    /**
     * Close the streams and socket.
     */
    public static void closeCrap() throws IOException {
        if (output != null) {
            output.close();
        }
        if (input != null) {
            input.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}
