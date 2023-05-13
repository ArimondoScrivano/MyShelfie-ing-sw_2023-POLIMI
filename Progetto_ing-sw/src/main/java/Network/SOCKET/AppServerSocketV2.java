package Network.SOCKET;

public class AppServerSocketV2 {
    public static void main(String[] args) {
        int defaultPort = 16001;
        System.out.println("Waiting for clients...");
        ConcreteServerSocketV2 serverSocket = new ConcreteServerSocketV2(defaultPort);
        Thread thread = new Thread(serverSocket, "serversocket");
        thread.start();
    }
}
