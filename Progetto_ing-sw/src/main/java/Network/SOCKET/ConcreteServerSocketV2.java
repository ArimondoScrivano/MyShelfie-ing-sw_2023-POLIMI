package Network.SOCKET;

import Network.messages.Message;
import Network.messages.MessageType;
import Network.messages.SocketMessages;
import controller.GameController;
import model.Shelf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.*;

public class ConcreteServerSocketV2 implements Runnable {
    private final int defaultPort;
    private ServerSocket serverSocket;
    private final Object lock;

    // How it works:
    //integer= indice della lobby, la mappa interna contiene l'associazione nome Giocatore, il suo socket
    private Map<Integer, Map<String, SocketClientHandler> > clientHandlerMap;
    private List<GameController> Lobby;
    private List<Message> LobbyMessage;

    public ConcreteServerSocketV2(int defaultPort){
        this.defaultPort=defaultPort;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock=new Object();
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
                //set the timeout to infinity
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

    public int createLobby(int numPlayers, String creatorLobby, Message message, SocketClientHandler clientHandler){
        System.out.println("Creating a lobby");
        GameController controller = new GameController(numPlayers, this, creatorLobby);
        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED));
        controller.setId(Lobby.indexOf(controller));
        this.clientHandlerMap.put(Lobby.indexOf(controller), new HashMap<>());
        addPlayer(message.getName(), clientHandler, Lobby.indexOf(controller), 1);
        return Lobby.indexOf(controller);
    }

    public int joinLobby(Message message, SocketClientHandler clientHandler){
        for (int i = 0; i < Lobby.size(); i++) {
            if (!Lobby.get(i).isFull()) {
                addPlayer(message.getName(), clientHandler, i, 0);
                return i; //index of lobby not full
            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller = new GameController(2, this);
        Lobby.add(controller);
        this.clientHandlerMap.put(Lobby.indexOf(controller), new HashMap<>());
        addPlayer(message.getName(), clientHandler, Lobby.indexOf(controller), 1);
        return Lobby.indexOf(controller);
    }

    public void addPlayer(String name, SocketClientHandler clientHandler, int index, int mult){
        System.out.println("Adding player "+ name);
        //Checking no duplicates of the name
        boolean flag=clientHandlerMap.get(index).containsKey(name);
        if(flag){
            clientHandler.sendMessage(new Message(name, SocketMessages.NAME_FAILED, index));
        }else{
            clientHandlerMap.get(index).put(name, clientHandler);
            clientHandlerMap.get(index).get(name).sendMessage(new Message(name, SocketMessages.LOGIN_REPLY, index));
            if(mult==0){
                int IndexPlayer=Lobby.get(index).getPlayersFilled();
                Lobby.get(index).createPlayer(IndexPlayer, name);
            }
        }

    }

    //Messages for game flow management
    public void onMessageReceived(Message message){
        System.out.println("Message received");
        switch(message.getMsg()){
            case IS_GAME_STARTING -> {
                checkGameStarting(message);
            }
            case IS_IT_MY_TURN -> {
                System.out.println("iuser " +message.getName() +" chiede se è il suo turno");
                if(nameCurrentPlayer(message.getNp()).equals(message.getName())){
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.MY_TURN, Lobby.get(message.getNp()).getDashboardTiles(), Lobby.get(message.getNp()).getCommonGoals(), Lobby.get(message.getNp()).playerTurn().getShelfMatrix(), Lobby.get(message.getNp()).playerTurn().getPersonalGoal()));
                }else{
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.WAITING_FOR_YOUR_TURN));
                }
            }
            case ARE_PARAMETERS_OK -> {
                List<List<Integer>> listToTest= message.getMyPossiblePick();
                int index= message.getNp();
                int numberTiles= listToTest.get(1).size();
                int possibleCol= message.getPossibleCol();
                List<Integer> xCoord= listToTest.get(0);
                List<Integer> yCoord= listToTest.get(1);
                //control parameters, if ok-> PARAMETERS_OK, IF ko-> PARAMETERS_KO
                if(pickableTiles( index, xCoord,  yCoord) &&  columnAvailable(index,  numberTiles, Lobby.get(message.getNp()).playerTurn().getShelf(), possibleCol )){
                    insertTiles(index,xCoord,yCoord,possibleCol);
                    finalPick(index, xCoord,yCoord);
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.PARAMETERS_OK,Lobby.get(message.getNp()).getDashboardTiles(), Lobby.get(message.getNp()).getCommonGoals(), Lobby.get(message.getNp()).playerTurn().getShelfMatrix(), Lobby.get(message.getNp()).playerTurn().getPersonalGoal()));
                }else{
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.PARAMETERS_KO));
                }
            }
        }
    }

    public String nameCurrentPlayer( int index) {
        return Lobby.get(index).playerTurn().getName();
    }

    //TODO:invio messaggio a tutti i client collegati a quella lobby che fa terminare immediatamente la partita
    public void onDisconnect(SocketClientHandler clientHandler){
        synchronized (lock){

        }
    }
    public void checkGameStarting(Message message){
        if (Lobby.get(message.getNp()).isFull()){

            for( String chiave : clientHandlerMap.get(message.getNp()).keySet()) {
                //For generalizzato sulla mappa
                clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.GAME_STARTING));
            }
        }else{
            for( String chiave : clientHandlerMap.get(message.getNp()).keySet()) {
                //For generalizzato sulla mappa
                clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.WAITING_FOR_OTHER_PLAYERS));
            }
        }
    }

    public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord)  {
        return Lobby.get(index).tileAvailablePick(xCoord, yCoord);
    }

    public boolean columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol) {
        return Lobby.get(index).columnAvailable(numTiles, myShelf, selectedCol);


    }

    public void finalPick(int index, List<Integer> xCord, List<Integer> yCord){
        Lobby.get(index).pickTiles(xCord,yCord);
    }



    //This method adds the tiles in the shelf
    public void insertTiles ( int LobbyReference, List<Integer> xCoord, List<Integer>  yCoord, int column) {
        Lobby.get(LobbyReference).insertTiles(xCoord,yCoord,column);
    }

}
