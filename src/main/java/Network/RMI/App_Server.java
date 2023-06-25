package Network.RMI;


import Network.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.RMISocketFactory;

public class App_Server {

    public static void main(String [] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "172.16.0.13");
            //16000- RMI REGISTRY PORT
            Registry registry = LocateRegistry.createRegistry(16000);

            System.out.println("Server is booting....");

            //16001- SOCKET PORT
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

