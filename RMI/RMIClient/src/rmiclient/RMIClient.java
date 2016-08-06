package rmiclient;

import java.rmi.Naming;
import rmiinterface.RMIServerIntf;

/**
 * Remote Method Invocation (RMI) demo, client side.
 * @author skorkmaz
 */
public class RMIClient {
    
    public static void main(String args[]) throws Exception {
        System.out.println("RMIClient started.");
        RMIServerIntf obj = (RMIServerIntf)Naming.lookup(RMIServerIntf.SERVER_NAME);
        System.out.println("message = " + obj.getMessage()); 
        System.out.println("customer name = " + obj.getCustomer().getName());
        System.out.println("customer id   = " + obj.getCustomer().getId());
    }
    
}
