package rmiserver;

/**
 * Remote Method Invocation (RMI) demo, server side.
 * @author skorkmaz
 */
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*; 
import java.rmi.server.UnicastRemoteObject;
import rmiinterface.Customer;
import rmiinterface.RMIServerIntf;
public class RMIServer extends UnicastRemoteObject implements RMIServerIntf {
    
 
    public RMIServer() throws RemoteException {
        super(0);    // required to avoid the 'rmic' step, see below
    }
 
    @Override
    public String getMessage() {
        return "Şamil ĞÜŞöçıİ";
    }
    
    @Override
    public Customer getCustomer() throws RemoteException {
        return new Customer("Şamil Korkmaz", 6);
    }
 
    public static void main(String args[]) throws Exception {        
        System.out.println("RMI server started.");
 
        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
 
        RMIServer obj = new RMIServer();
 
        // Bind this object instance to the name "RmiServer"
        Naming.rebind(RMIServerIntf.SERVER_NAME, obj);
        System.out.println("PeerServer bound in registry.");
    }
}