package Network.RMI;

import Network.SOCKET.ConcreteSocketClient;
import Network.SOCKET.ConcreteSocketServer;

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

        try{
            System.out.println("creating the socket server");
            ConcreteSocketServer me= new ConcreteSocketServer();
            System.out.println("socketServer created");
            ServerSocket mySs= me.getSocketServer();
            System.out.println("Socket Server created.");
            while(true){
                System.out.println("entering the while");
                Socket mySocket= me.acceptConnections();
                System.out.println("connection accepted");
                BufferedReader inputStream= new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                System.out.println("client input stream created");
                PrintWriter outputStream= new PrintWriter(mySocket.getOutputStream(), true);
                System.out.println("client output stream created");
                InputStream objectInputStream= mySocket.getInputStream();
                ObjectInputStream ois= new ObjectInputStream(objectInputStream);
                System.out.println("client object input stream created");
                OutputStream objectOutputStream= mySocket.getOutputStream();
                ObjectOutputStream oos=new ObjectOutputStream(objectOutputStream);
                System.out.println("client object output stream created");
                String clientMessage= inputStream.readLine();
                if(clientMessage.equals("START")){
                    me.startGame(outputStream);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

