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

public class SocketClientV2 {
    private final Socket socket;
    private final ClientControllerV2 clientController;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final ExecutorService readExecutionQueue;
    private ScheduledExecutorService ping;

    public SocketClientV2(String address, int port, ClientControllerV2 controller) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(address, port), 10000);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.clientController=controller;
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
        this.ping=Executors.newSingleThreadScheduledExecutor();
    }

    public void sendMessage(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            disconnect();
        }
    }

    public void readMessage() {

        readExecutionQueue.execute(()->{
            while(!readExecutionQueue.isShutdown()){
                Message message;
                try{
                    message = (Message) inputStream.readObject();
                    if(message != null && message.getMsg()!=SocketMessages.PING_MESSAGE){
                        if(message.getMsg()==SocketMessages.LOGIN_REPLY){
                            //here we have created a new game and the player is inserted into the map
                            System.out.println("Client added to the game");
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

    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pingMessage(boolean enable){
        if(enable){
            ping.schedule(()->sendMessage(new Message("ping", SocketMessages.PING_MESSAGE)), 1000, TimeUnit.MILLISECONDS);
            //ping.scheduleAtFixedRate(()->sendMessage(new Message("ping", SocketMessages.PING_MESSAGE)), 0, 1000, TimeUnit.MILLISECONDS);
        }else{
            ping.shutdownNow();
        }
    }
}
