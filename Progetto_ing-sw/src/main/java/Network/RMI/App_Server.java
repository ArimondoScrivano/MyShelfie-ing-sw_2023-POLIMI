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
            ServerSocket mySs= me.getSocketServer();
            while(true){
                System.out.println("waiting for connections");
                Socket mySocket= me.acceptConnections();
                BufferedReader inputStream= new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                PrintWriter outputStream= new PrintWriter(mySocket.getOutputStream(), true);
                InputStream objectInputStream= mySocket.getInputStream();
                ObjectInputStream ois= new ObjectInputStream(objectInputStream);
                OutputStream objectOutputStream= mySocket.getOutputStream();
                ObjectOutputStream oos=new ObjectOutputStream(objectOutputStream);
                System.out.println("calling receiveMessages()");
                me.receiveMessages(inputStream, outputStream, ois, oos);
                /*String clientMessage= inputStream.readLine();
                if(clientMessage.equals("START")){
                    me.startGame(outputStream);
                }*/

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

