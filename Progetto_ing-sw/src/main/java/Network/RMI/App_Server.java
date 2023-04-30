package Network.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App_Server {

    public static void main(String [] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(16000);
            registry.rebind("server", new ConcreteServerRMI());

            /*System.out.println("Server is booting....");

            System.setProperty("java.rmi.server.hostname","127.0.0.1");

            System.out.println("Server is setting property....");
            // We create objects from ConcreteServerRMI.java class and share them using
            Server_RMI server= new ConcreteServerRMI();

            System.out.println("Server created");
            // Get the RMI registry.
            //Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9000);
            System.out.println("Registry located");

            // Registered the exported object in rmi registry so that client can
            // lookup in this registry and call the object methods.
            registry.rebind("server", server);
            //registry.bind("server", server);

            System.out.println("Binding");*/

        } catch (Exception e) {

            System.out.println("    qualcosa è andato storto " + e);

        }

    }
}

