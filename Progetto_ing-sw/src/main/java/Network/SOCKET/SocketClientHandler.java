package Network.SOCKET;

import Network.Server;
import Network.messages.Message;
import Network.messages.MessageType;
import Network.messages.SocketMessages;
import view.Cli;

import java.io.*;
import java.net.Socket;

public class SocketClientHandler implements Runnable{
    private final Socket client;

    private int idLobby;
    private final Server socketServer;
    private boolean connected;
    private final Object inputLock;
    private final Object outputLock;
    private ObjectOutputStream output; // server to client
    private ObjectInputStream input; // client to server


    //newConstructor
    //.________________________________.//
    public SocketClientHandler(Server server, Socket client) {
        this.socketServer = server;
        this.client = client;
        this.connected = true;
        //those are the inizialization value of the index of the game and the index of the player
        this.idLobby= 0;
        this.inputLock = new Object();
        this.outputLock = new Object();

        try {
            this.output = new ObjectOutputStream(client.getOutputStream());
            this.input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //.------------------------------------------.//
    @Override
    public void run() {
        try {
            connect();
        } catch (IOException e) {
            disconnect();
        }
    }

    public boolean isConnected() {
        return connected;
    }

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
            e.printStackTrace();
        }
        client.close();
    }

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

            socketServer.onDisconnect(this);
        }
    }

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
