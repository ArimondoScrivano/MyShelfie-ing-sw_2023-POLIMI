package Network.RMI;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App_Server {

    public static void main(String [] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(16000);
            System.out.println("Server is booting....");
            registry.rebind("server", new ConcreteServerRMI());
            System.out.println("Server created");
        } catch (Exception e) {
            System.out.println("Something went wrong :( " + e);
        }


    }
}

