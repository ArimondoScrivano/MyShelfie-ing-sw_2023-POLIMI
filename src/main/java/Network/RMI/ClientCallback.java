package Network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    /**
     * Method that control the connection of an RMI client
     * @throws RemoteException  if a remote communication error occurs (rmi client not connected)
     * */
    void CheckConnectionClient() throws RemoteException;

}
