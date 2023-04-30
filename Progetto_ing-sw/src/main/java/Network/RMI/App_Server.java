package Network.RMI;
// package Server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import controller.*;
public class App_Server {

    public static void main(String [] args) {
        try {

            System.out.println("Server is booting....");
            System.setProperty("java.rmi.server.hostname","127.0.0.1");

            // We create objects from ConcreteServerRMI.java class and share them using
            Server_RMI server= new ConcreteServerRMI();

            // Get the RMI registry.
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9000);

            // Registered the exported object in rmi registry so that client can
            // lookup in this registry and call the object methods.
            registry.bind("server", server);

        } catch (Exception e) {
            System.out.println("Server error" + e);

        }

    }
}

