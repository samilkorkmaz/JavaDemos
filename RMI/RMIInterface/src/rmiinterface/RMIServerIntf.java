package rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author skorkmaz
 */
public interface RMIServerIntf extends Remote {
    public static final String SERVER_NAME = "//localhost/RMIServer";
    public String getMessage() throws RemoteException;
    public Customer getCustomer() throws RemoteException;
}
