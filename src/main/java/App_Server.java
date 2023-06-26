import Network.Server;

import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class App_Server {

    public static void main(String [] args) {
        try {
            Scanner in = new Scanner(new InputStreamReader(System.in));
            System.out.println("insert ipAddress: ");
            String input = in.nextLine();
            while (input.trim().isEmpty()) {
                input= in.nextLine();
            }
            System.setProperty("java.rmi.server.hostname", input);
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

