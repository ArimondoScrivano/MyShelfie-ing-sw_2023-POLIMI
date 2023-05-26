package Network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    void CheckConnectionClient() throws RemoteException;

}
