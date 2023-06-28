package Network.SOCKET;

import Network.messages.Message;
import Network.messages.SocketMessages;
import controller.ClientControllerV2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The SocketClientHandler class is responsible for handling client connections and communication
 * in the server-side of a socket-based network implementation.
 * It runs in a separate thread and establishes a connection with the client socket,
 * handles incoming messages, and sends messages to the connected client.
 */
public class SocketClientV2 {
    private final Socket socket;
    private final ClientControllerV2 clientController;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final ExecutorService readExecutionQueue;
    private final ScheduledExecutorService ping;

    /**
     * Constructs a new instance of SocketClientV2.
     *
     * @param address         The IP address or hostname of the server to connect to.
     * @param port            The port number of the server to connect to.
     * @param controller      The client controller responsible for handling client events and messages.
     * @throws IOException    If an I/O error occurs while establishing the connection.
     */
    public SocketClientV2(String address, int port, ClientControllerV2 controller) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(address, port), 10000);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.clientController=controller;
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
        this.ping=Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Sends a message to the connected server.
     *
     * @param message The message to be sent.
     */
    public void sendMessage(Message message) {
        try {
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Reads incoming messages from the server.
     * This method is executed in a separate thread and continuously reads messages from the input stream.
     * It dispatches the received messages to the client controller for further processing.
     * The method terminates when the readExecutionQueue is shutdown or an IO or ClassNotFoundException occurs.
     */
    public void readMessage() {
        readExecutionQueue.execute(()->{
            while(!readExecutionQueue.isShutdown()){
                Message message;
                try{
                    message = (Message) inputStream.readObject();
                    if(message != null && message.getMsg()!=SocketMessages.PING_MESSAGE){
                        if(message.getMsg()==SocketMessages.LOGIN_REPLY){
                            clientController.setIdLobby(message.getNp());
                            //here we have created a new game and the player is inserted into the map
                            System.out.println("Client added to the game");
                            sendMessage(new Message(clientController.getName(),SocketMessages.IS_GAME_STARTING, clientController.getIdLobby()));
                        }else{
                            clientController.onMessageReceived(message);
                        }
                    }
                }catch(IOException | ClassNotFoundException e){
                    disconnect();
                    readExecutionQueue.shutdown();
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Disconnects the client from the server.
     * If the client is currently connected, it shuts down the read execution queue,
     * closes the client socket, prints a disconnection message, and exits the program with an error status.
     * This method does nothing if the client is already disconnected.
     */
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                socket.close();
                System.err.println("DISCONNECTION");
                System.exit(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Enables or disables the sending of ping messages to the server.
     *
     * @param enable true to enable ping messages, false to disable them.
     *               If enabled, ping messages are scheduled to be sent every 1000 milliseconds.
     *               If disabled, the scheduled ping messages are canceled.
     */
    public void pingMessage(boolean enable){
        if(enable){
            ping.schedule(()->sendMessage(new Message("ping", SocketMessages.PING_MESSAGE)), 1000, TimeUnit.MILLISECONDS);
        }else{
            ping.shutdownNow();
        }
    }
}
