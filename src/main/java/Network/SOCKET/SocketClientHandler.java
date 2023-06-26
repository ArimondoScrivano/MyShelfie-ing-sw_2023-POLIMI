package Network.SOCKET;

import Network.Server;
import Network.messages.Message;
import Network.messages.SocketMessages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientHandler implements Runnable{
    private final Socket client;

    private int idLobby;
    private final Server socketServer;
    private boolean connected;
    private final Object inputLock;
    private final Object outputLock;
    private ObjectOutputStream output;
    private ObjectInputStream input;


    //newConstructor
    //.________________________________.//
    /**
     * Constructs a new instance of SocketClientHandler.
     *
     * @param server The server instance to which the client handler belongs.
     * @param client The socket representing the client connection.
     */
    public SocketClientHandler(Server server, Socket client) {
        this.socketServer = server;
        this.client = client;
        this.connected = true;
        //Those are the initialization value of the index of the game and the index of the player
        this.idLobby= 0;
        this.inputLock = new Object();
        this.outputLock = new Object();

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.err.println("Error in connection");
            System.exit(0);
        }
    }
    //.------------------------------------------.//

    /**
     * Executes the main logic of the SocketClientHandler in a separate thread.
     * This method is automatically invoked when the thread is started.
     * It establishes a connection with the client and handles any exceptions that may occur during the process.
     */
    @Override
    public void run() {
        try {
            connect();
        } catch (IOException e) {
            disconnect();
        }
    }


    /**
     * Establishes the connection with the client and handles incoming messages.
     * This method runs in a loop until the current thread is interrupted or the connection is closed.
     *
     * @throws IOException if an I/O error occurs while reading from the client.
     */
    public void connect() throws IOException{
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) input.readObject();

                    if (message != null && message.getMsg()!=SocketMessages.PING_MESSAGE) {
                        if (message.getMsg() == SocketMessages.NEW_GAME) {
                            System.out.println("Player added " + message.getName());
                            this.idLobby=socketServer.createLobby(message.getNp(), message.getName(), message, this);
                            //here we create a Lobby and return the index of it
                        }else if(message.getMsg() == SocketMessages.JOIN_LOBBY){
                            this.idLobby=socketServer.joinLobby(message, this);
                        }else if(message.getMsg() == SocketMessages.NAME_UPDATE){
                            socketServer.addPlayer(message.getName(), this, idLobby,0);
                        }else{
                            //Generic message to read and manage
                            socketServer.onMessageReceived(message);
                        }
                    }
                }
            }
        } catch (ClassCastException | ClassNotFoundException e) {
            //For debugging purpose
            //e.printStackTrace();
            System.err.println("Error receiving a message");
        }
        client.close();
    }


    /**
     * Disconnects the client from the server.
     * If the client is currently connected, it closes the client socket, interrupts the current thread,
     * and notifies the server of the disconnection.
     * This method does nothing if the client is already disconnected.
     */
    public void disconnect() {
        if (connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            connected = false;
            Thread.currentThread().interrupt();

            socketServer.onDisconnect(this, this.idLobby);
        }
    }


    /**
     * Sends a message to the connected client.
     *
     * @param message The message to be sent.
     */
    public void sendMessage(Message message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.flush();
                output.reset();
            }
        } catch (IOException e) {
            disconnect();
        }
    }
}
