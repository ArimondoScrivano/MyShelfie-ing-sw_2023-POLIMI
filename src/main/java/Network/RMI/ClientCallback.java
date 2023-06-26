package Network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    /**
     * Method that control the connection of an RMI client
     * */
    void CheckConnectionClient() throws RemoteException;

}
