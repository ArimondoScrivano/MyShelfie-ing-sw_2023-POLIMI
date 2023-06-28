package Network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The ClientCallback interface defines the contract for RMI client callbacks.
 * It extends the Remote interface to enable remote method invocation.
 */
public interface ClientCallback extends Remote {
    /**
     * Method that control the connection of an RMI client
     * @throws RemoteException  if a remote communication error occurs (rmi client not connected)
     * */
    void CheckConnectionClient() throws RemoteException;

}
