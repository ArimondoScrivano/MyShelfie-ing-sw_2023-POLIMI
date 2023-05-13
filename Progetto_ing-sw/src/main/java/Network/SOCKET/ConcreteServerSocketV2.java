package Network.SOCKET;

import Network.messages.Message;
import Network.messages.MessageType;
import Network.messages.SocketMessages;
import controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ConcreteServerSocketV2 implements Runnable {
    private final int defaultPort;
    private ServerSocket serverSocket;
    private final Object lock;
    private Map<String, SocketClientHandler> clientHandlerMap;
    private List<GameController> Lobby;
    private List<String> players;
    private List<Message> LobbyMessage;

    public ConcreteServerSocketV2(int defaultPort){
        this.defaultPort=defaultPort;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock=new Object();
        this.players=Collections.synchronizedList(new ArrayList<>());
        this.Lobby=new ArrayList<>();
        this.LobbyMessage=new ArrayList<>();
    }

    @Override
    public void run() {
        try{
            serverSocket = new ServerSocket(defaultPort);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        while(!Thread.currentThread().isInterrupted()){
            try{
                Socket client = serverSocket.accept();

                client.setSoTimeout(0);

                SocketClientHandler clientHandler = new SocketClientHandler(this, client);
                System.out.println("Client connected at address: "+client.getInetAddress());
                Thread thread = new Thread(clientHandler, "handler"+client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int createLobby(int numPlayers, String creatorLobby){
        System.out.println("Creating a lobby");
        GameController controller = new GameController(numPlayers, this, creatorLobby);
        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED));
        controller.setId(Lobby.indexOf(controller));
        return Lobby.indexOf(controller);
    }

    public int joinLobby(){
        for (int i = 0; i < Lobby.size(); i++) {
            if (!Lobby.get(i).isFull()) {
                return i; //index of lobby not full
            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller = new GameController(2, this);
        Lobby.add(controller);
        return Lobby.indexOf(controller);
    }

    //TODO: fix add player
    public void addPlayer(String name, SocketClientHandler clientHandler){
        System.out.println("Adding player "+ name);
        clientHandlerMap.put(name, clientHandler);
        clientHandlerMap.get(name).sendMessage(new Message(name, SocketMessages.LOGIN_REPLY));
    }

    //Messages for game flow management
    public void onMessageReceived(Message message){
        System.out.println("Message received");
        switch(message.getMsg()){
            case NAME_UPDATE -> {

            }
            case NEW_GAME -> {
                int index=createLobby(message.getNp(), message.getName());
                clientHandlerMap.get(message.getName()).sendMessage(new Message(message.getName(), SocketMessages.LOBBY_CREATED, index));
                players.add(message.getName());
            }
            case IS_IT_MY_TURN -> {
                System.out.println("Client chiede se è il suo turno");
                //Check if the game is started-->lobby full
                if(!Lobby.get(message.getNp()).isFull()){
                    clientHandlerMap.get(message.getName()).sendMessage(new Message(message.getName(), SocketMessages.WAITING_FOR_OTHER_PLAYERS));
                }else{
                    clientHandlerMap.get(message.getName()).sendMessage(new Message(message.getName(), SocketMessages.WAITING_FOR_YOUR_TURN));
                }
            }
            case JOIN_LOBBY->{
                System.out.println("Client joining the lobby");
                int index=joinLobby();
                //TODO: aggiungere il player in maniera definitiva
                clientHandlerMap.get(message.getName()).sendMessage(new Message(message.getName(), SocketMessages.ADDED_TO_LOBBY, index));
                players.add(message.getName());
                if(!Lobby.get(message.getNp()).isFull()){
                    clientHandlerMap.get(message.getName()).sendMessage(new Message(message.getName(), SocketMessages.WAITING_FOR_OTHER_PLAYERS));
                }else{
                    //Lobby piena invio messaggi a tutti i client collegati alla lobby
                    for(String p:players){
                        System.out.println(p);
                    }
                    for(int i=0; i<Lobby.size(); i++){
                        clientHandlerMap.get(players.get(i)).sendMessage(new Message(players.get(i), SocketMessages.GAME_STARTING));
                    }
                }
            }
        }
    }

    //TODO:invio messaggio a tutti i client collegati a quella lobby che fa terminare immediatamente la partita
    public void onDisconnect(SocketClientHandler clientHandler){
        synchronized (lock){

        }
    }
}
