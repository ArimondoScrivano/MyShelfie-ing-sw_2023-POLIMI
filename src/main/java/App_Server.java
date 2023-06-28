import Network.Server;

import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * The main class for starting the server application.
 */
public class App_Server {

    /**
     * The main method that starts the server application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new InputStreamReader(System.in));
            System.out.println("insert ipAddress: ");
            String input = in.nextLine();
            while (input.trim().isEmpty()) {
                input = in.nextLine();
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
