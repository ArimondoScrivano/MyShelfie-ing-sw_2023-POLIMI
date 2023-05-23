package Network.RMI;


import Network.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App_Server {

    public static void main(String [] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(16000);
            System.out.println("Server is booting....");
            int defaultPort = 16001;
            Server server = new Server(defaultPort);
            registry.rebind("server", server);
            Thread thread = new Thread(server, "server");
            thread.start();
            System.out.println("Server created");
        } catch (Exception e) {
            System.out.println("Something went wrong :( " + e);
        }


    }
}

